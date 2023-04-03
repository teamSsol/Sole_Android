package cmc.sole.android.Home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.MyPage.MyPageActivity
import cmc.sole.android.Home.Retrofit.*
import cmc.sole.android.Home.Search.SearchActivity
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.StartCourseTagActivity
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    HomePopularCourseView, HomeDefaultCourseView, HomeGetCurrentGPSView, HomeUpdateCurrentGPSView, HomeScrapAddAndCancelView {

    private lateinit var popularCourseRVAdapter: HomePopularCourseRVAdapter
    private lateinit var myCourseRVAdapter: HomeDefaultCourseRVAdapter
    private var popularCourseList = ArrayList<PopularCourse>()
    private var myCourseList = ArrayList<DefaultCourse>()
    private lateinit var homeService: HomeService
    var courseId: Int? = null
    var popularAPIFlag = false
    var tasteAPIFlag = false

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
        initAPIService()
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEMP-TEST", "onResume")
        myCourseRVAdapter.clearItems()
        homeService.getHomePopularCourse()
        homeService.getHomeDefaultCourse(courseId, "")
    }

    private fun initAdapter() {
        // CONDITION: 이 주변 인기 코스 7개로 고정
        popularCourseRVAdapter = HomePopularCourseRVAdapter(popularCourseList)
        binding.homePopularCourseRv.adapter = popularCourseRVAdapter
        binding.homePopularCourseRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.homePopularCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popularCourseRVAdapter.setOnItemClickListener(object: HomePopularCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: PopularCourse, position: Int) {
                val intent = Intent(activity, CourseDetailActivity::class.java)
                intent.putExtra("courseId", data.courseId)
                startActivity(intent)
            }
        })

        // CONDITION: 내 취향 코스 5개 + 더 보기 버튼 이용
        myCourseRVAdapter = HomeDefaultCourseRVAdapter(myCourseList)
        binding.homeMyCourseRv.adapter = myCourseRVAdapter
        binding.homeMyCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 15))
        binding.homeMyCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myCourseRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int, mode: String) {
                if (mode == "all") {
                    val intent = Intent(activity, CourseDetailActivity::class.java)
                    intent.putExtra("courseId", data.courseId)
                    intent.putExtra("like", data.like)
                    Log.d("API-TEST", "like out = ${data.like}")
                    startActivity(intent)
                } else if (mode == "heart") {

                }
            }
        })
    }

    private fun initClickListener() {
        binding.homeSearchIv.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        binding.homeProfileIv.setOnClickListener {
            startActivity(Intent(activity, MyPageActivity::class.java))
        }

        binding.homeFb.setOnClickListener {
            startActivity(Intent(activity, MyCourseWriteActivity::class.java))
        }

        binding.homePopularCourseLayout.setOnClickListener {
            // UPDATE: 현재 위치 변경
            if (getCurrentLocation() != null)
                homeService.updateCurrentGPS(getCurrentLocation()!!)
            popularCourseRVAdapter.clearItems()
            homeService.getHomePopularCourse()
        }

        binding.homeMyCourseSettingTv.setOnClickListener {
            startActivity(Intent(activity, StartCourseTagActivity::class.java))
        }
    }

    private fun getCurrentLocation(): HomeCurrentGPSRequest? {
        val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        // MEMO: 매니페스트에 권한이 추가되어 있다해도 한번 더 확인 필요
        if (ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_FINE_LOCATION), 0)
        } else {
            when {
                // MEMO: 프로바이더 활성화 여부 체크
                isNetworkEnabled -> {
                    val location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) //인터넷기반으로 위치를 찾음
                    return HomeCurrentGPSRequest(location?.latitude!!, location?.longitude!!)
                }
                isGPSEnabled -> {
                    val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) //GPS 기반으로 위치를 찾음
                    return HomeCurrentGPSRequest(location?.latitude!!, location?.longitude!!)
                }
                else -> { }
            }
        }

        return null
    }

    private fun initAPIService() {
        homeService = HomeService()
        homeService.setHomePopularCourseView(this)
        homeService.setHomeDefaultCourseView(this)
        homeService.setHomeGetCurrentGPSView(this)
        homeService.setHomeUpdateCurrentGPSView(this)
        homeService.getHomePopularCourse()
        homeService.getHomeDefaultCourse(courseId, "")
        homeService.getCurrentGPS()
    }

    override fun homePopularCourseSuccessView(homePopularResponse: HomePopularResponse) {
        if (homePopularResponse.data?.size == 0) {
            binding.homePopularCourseEmpty.visibility = View.VISIBLE
        } else {
            binding.homePopularCourseEmpty.visibility = View.INVISIBLE
        }
    }

    override fun homePopularCourseFailureView() { }

    override fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {
        if (homeDefaultResponse.data.size == 0) {
            binding.homeMyCourseEmpty.visibility = View.VISIBLE
        } else {
            binding.homeMyCourseEmpty.visibility = View.INVISIBLE
        }
        myCourseRVAdapter.addAllItems(homeDefaultResponse.data)
    }

    override fun homeDefaultCourseFailureView() { }

    override fun homeGetCurrentGPSSuccessView() { }

    override fun homeGetCurrentGPSFailureView() { }
    override fun homeUpdateCurrentGPSSuccessView(homeCurrentGPSResult: HomeCurrentGPSResult) {
        binding.homePopularCourseSettingLocationIv.text = homeCurrentGPSResult.currentGps.address
    }

    override fun homeUpdateCurrentGPSFailureView() { }
    override fun homeScrapAddAndCancelSuccessView() {
        TODO("Not yet implemented")
    }

    override fun homeScrapAddAndCancelFailureView() {
        TODO("Not yet implemented")
    }
}