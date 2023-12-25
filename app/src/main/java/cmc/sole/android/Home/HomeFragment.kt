package cmc.sole.android.Home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.MyPage.MyPageActivity
import cmc.sole.android.Home.Retrofit.*
import cmc.sole.android.Search.SearchActivity
import cmc.sole.android.Write.MyCourseWriteActivity
import cmc.sole.android.StartCourseTagActivity

import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentHomeBinding
import androidx.fragment.app.Fragment
import cmc.sole.android.Course.ScrapSelectFolderBottomFragment
import cmc.sole.android.R
import cmc.sole.android.getPlaceCategories
import cmc.sole.android.getTransCategories
import cmc.sole.android.getWithCategories

class HomeFragment: Fragment(),
    HomePopularCourseView, HomeDefaultCourseView, HomeFilterCourseView, HomeGetCurrentGPSView, HomeUpdateCurrentGPSView, HomeScrapAddAndCancelView {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var popularCourseRVAdapter: HomePopularCourseRVAdapter
    private lateinit var myCourseRVAdapter: HomeDefaultCourseRVAdapter
    private var popularCourseList = ArrayList<PopularCourse>()
    private var myCourseList = ArrayList<DefaultCourse>()
    private lateinit var homeService: HomeService
    var courseId: Int? = null
    private var lastCourseId: Int? = null
    var clickItemIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initAdapter()
        initClickListener()
        initAPIService()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
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
                clickItemIndex = position
                if (mode == "all") {
                    val intent = Intent(activity, CourseDetailActivity::class.java)
                    intent.putExtra("courseId", data.courseId)
                    intent.putExtra("like", data.like)
                    startActivity(intent)
                } else if (mode == "heart") {
                    if (data.like) {
                        homeService.scrapAddAndCancel(data.courseId, null)
                    } else {
                        val scrapSelectFolderBottomFragment = ScrapSelectFolderBottomFragment()
                        var bundle = Bundle()
                        bundle.putInt("courseId", data.courseId,)
                        scrapSelectFolderBottomFragment.arguments = bundle
                        scrapSelectFolderBottomFragment.show(activity!!.supportFragmentManager, "ScrapSelectFolderBottomFragment")
                        scrapSelectFolderBottomFragment.setOnDialogFinishListener(object: ScrapSelectFolderBottomFragment.OnDialogFinishListener {
                            override fun finish(isSuccess: Boolean) {
                                if (isSuccess) {
                                    myCourseRVAdapter.changeLikeStatus(position)
                                    myCourseRVAdapter.notifyItemChanged(position)
                                }
                            }
                        })
                    }
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

        binding.homePopularCourseLayoutCv.setOnClickListener {
            val currentLocation = getCurrentLocation()
            if (currentLocation != null) {
                homeService.updateCurrentGPS(currentLocation)
            }
            popularCourseRVAdapter.clearItems()
            homeService.getHomePopularCourse()
        }

        binding.homeMyCourseSettingTv.setOnClickListener {
            val intent = Intent(activity, StartCourseTagActivity::class.java)
            intent.putExtra("flag", "homeMyCourseSetting")
            startActivity(intent)
        }

        binding.courseMoreCv.setOnClickListener {
            homeService.getHomeDefaultCourse(lastCourseId, "")
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
                    return HomeCurrentGPSRequest(location?.latitude!!, location.longitude)
                }
                isGPSEnabled -> {
                    val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) //GPS 기반으로 위치를 찾음
                    return HomeCurrentGPSRequest(location?.latitude!!, location.longitude)
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
        homeService.setHomeScrapAddAndCancelView(this)
        homeService.getHomePopularCourse()
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

        if (homeDefaultResponse.data.size != 0) {
            // MEMO: 마지막 페이지가 아니라면 더 보기 버튼 보여주기
            var lastCourse = homeDefaultResponse.data[homeDefaultResponse.data.size - 1]
            if (!lastCourse.finalPage) {
                lastCourseId = lastCourse.courseId
                binding.courseMoreCv.visibility = View.VISIBLE
            } else binding.courseMoreCv.visibility = View.GONE
        }

        if (lastCourseId == null) {
            myCourseRVAdapter.clearItems()
        }

        myCourseRVAdapter.addAllItems(homeDefaultResponse.data)
    }

    override fun homeDefaultCourseFailureView() { }

    override fun homeGetCurrentGPSSuccessView(currentGPS: String) {
        binding.homePopularCourseSettingLocationIv.text = currentGPS
    }

    override fun homeGetCurrentGPSFailureView() { }
    override fun homeUpdateCurrentGPSSuccessView(homeCurrentGPSResult: HomeCurrentGPSResult) {
        binding.homePopularCourseSettingLocationIv.text = homeCurrentGPSResult.currentGps.address
    }

    override fun homeUpdateCurrentGPSFailureView() { }
    override fun homeScrapAddAndCancelSuccessView() {
        myCourseRVAdapter.changeLikeStatus(clickItemIndex!!)
        myCourseRVAdapter.notifyItemChanged(clickItemIndex!!)
    }

    override fun homeScrapAddAndCancelFailureView() { }
    
    // TODO: 필터 설정 오류 생기면 이 부분에 직접 필터 넣어주기
    override fun homeFilterCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {
        TODO("Not yet implemented")
    }

    override fun homeFilterCourseFailureView() {
        TODO("Not yet implemented")
    }
}