package cmc.sole.android.Course

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Follow.Retrofit.FollowUnfollowView
import cmc.sole.android.Home.*
import cmc.sole.android.Home.Retrofit.HomeCourseDetailView
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.ScrapOnOffView
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.MyCourse.Retrofit.MyCourseReportView
import cmc.sole.android.MyCourse.Retrofit.MyCourseService
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ActivityCourseDetailBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.overlay.PolylineOverlay
import kotlin.math.roundToInt

class CourseDetailActivity: AppCompatActivity(), OnMapReadyCallback,
    HomeCourseDetailView, HomeScrapAddAndCancelView, ScrapOnOffView, FollowUnfollowView {

    lateinit var binding: ActivityCourseDetailBinding
    private lateinit var courseDetailCourseRVAdapter: CourseDetailCourseRVAdapter
    private var courseList = ArrayList<PlaceResponseDtos>()
    private lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()
    lateinit var homeService: HomeService
    lateinit var followService: FollowService
    var courseId = -1
    var like = false
    var followStatus = "FOLLOWER"
    var memberId = -1
    var pointList = mutableListOf<LatLng>()
    private var checkWriter: Boolean = false

    // NaverMap
    lateinit var naverMap: NaverMap

    // NaverMap Course
    var path = PathOverlay()
    var polyline  = PolylineOverlay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseDetailBinding.inflate(layoutInflater)

        courseId = intent.getIntExtra("courseId", -1)
        like = intent.getBooleanExtra("like", false)

        if (like) {
            binding.courseDetailTitleHeartIv.setImageResource(R.drawable.ic_heart_color)
        } else {
            binding.courseDetailCourseHeartIv.setImageResource(R.drawable.ic_heart)
        }

        var mapFragment = supportFragmentManager.findFragmentById(R.id.course_detail_map) as MapFragment?
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.course_detail_map, mapFragment).commit()
        }

        mapFragment!!.getMapAsync(this)

        initService(courseId)
        initClickListener()
        initAdapter()

        setContentView(binding.root)
    }

    private fun initService(courseId: Int) {
        homeService = HomeService()
        homeService.setHomeCourseDetailView(this)
        homeService.getHomeDetailCourse(courseId)
        homeService.setHomeScrapAddAndCancelView(this)
        homeService.setScrapOnOffView(this)

        followService = FollowService()
        followService.setFollowUnfollowView(this)
    }

    private fun initClickListener() {
        binding.courseDetailBackIv.setOnClickListener {
            finish()
        }

        binding.courseDetailOptionIv.setOnClickListener {
            val courseDetailOptionBottomFragment = CourseDetailOptionBottomFragment()
            var bundle = Bundle()
            bundle.putInt("courseId", courseId)
            bundle.putBoolean("checkWriter", checkWriter)
            courseDetailOptionBottomFragment.arguments = bundle
            courseDetailOptionBottomFragment.show(supportFragmentManager, "CourseDetailOptionBottom")
        }

        binding.courseDetailTitleHeartIv.setOnClickListener {
            if (like) {
                Log.d("API-TEST", "Like 삭제")
                homeService.scrapOnOff(courseId)
            } else {
                Log.d("API-TEST", "Like 추가")
                val scrapSelectFolderBottomFragment = ScrapSelectFolderBottomFragment()
                var bundle = Bundle()
                bundle.putInt("courseId", courseId)
                scrapSelectFolderBottomFragment.arguments = bundle
                scrapSelectFolderBottomFragment.show(supportFragmentManager, "ScrapSelectFolderBottomFragment")
                scrapSelectFolderBottomFragment.setOnDialogFinishListener(object: ScrapSelectFolderBottomFragment.OnDialogFinishListener {
                    override fun finish(isSuccess: Boolean) {
                        // UPDATE: 성공 여부 받아오기
                        if (isSuccess) {
                            like = !like
                            binding.courseDetailTitleHeartIv.setImageResource(R.drawable.ic_heart_color)
                        }
                        Log.d("API-TEST", "isSuccess = $isSuccess")
                    }
                })
            }
        }

        binding.itemFollowFollowBtn.setOnClickListener {
            followService.followUnfollow(memberId)
        }

        binding.itemFollowFollowingBtn.setOnClickListener {
            followService.followUnfollow(memberId)
        }
    }

    private fun initAdapter() {
        courseDetailCourseRVAdapter = CourseDetailCourseRVAdapter(courseList)
        binding.courseDetailCourseRv.adapter = courseDetailCourseRVAdapter
        binding.courseDetailCourseRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.courseDetailTagRv.adapter = tagRVAdapter
        val layoutManager = FlexboxLayoutManager(this)
        binding.courseDetailTagRv.layoutManager = layoutManager
        binding.courseDetailTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.courseDetailTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // MEMO: Polyline 넣기
        if (polyline.coords.size >= 2) {
            polyline.coords = pointList
            polyline.setPattern(10, 5)
            polyline.width = 10
            polyline.color = ContextCompat.getColor(this, R.color.main)
            polyline.map = naverMap
        }

        // MEMO: CameraPosition 변경
        // UPDATE: 각 코스마다 특정 CameraPosition 입력해주기
        if (pointList.size != 0) {
            val location = pointList[0]
            // 카메라 위치와 줌 조절(숫자가 클수록 확대)
            val cameraPosition = CameraPosition(location, 15.0)
            naverMap.cameraPosition = cameraPosition
        }

        // MEMO: Marker 모양 변경
        // UPDATE: 특정 위치 좌표를 통해 숫자 입력해주기
        for (i in 0 until pointList.size) {
            val marker = Marker()
            if (i == 0) {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_course_no_1)
            } else if (i == 1) {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_course_no_2)
            } else if (i == 2) {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_course_no_3)
            } else if (i == 3) {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_course_no_4)
            } else {
                marker.icon = OverlayImage.fromResource(R.drawable.ic_course_no_1)
            }
            marker.position = pointList[i]
            marker.map = naverMap
        }
    }

    override fun homeCourseDetailSuccessView(homeCourseDetailResult: HomeCourseDetailResult) {
        for (i in 0 until homeCourseDetailResult.categories.size) {
            tagRVAdapter.addItem(Translator.tagEngToKor(this, homeCourseDetailResult.categories.elementAt(i).toString()))
        }
        tagRVAdapter.addItem("")

        checkWriter = homeCourseDetailResult.checkWriter
        if (homeCourseDetailResult.checkWriter) {
            binding.courseDetailTitleHeartIv.visibility = View.GONE
        } else {
            binding.courseDetailTitleHeartIv.visibility = View.VISIBLE
        }

        binding.courseDetailCourseContent.text = homeCourseDetailResult.description

        binding.courseDetailCourseDistanceTv.text = ((homeCourseDetailResult.distance * 100.0).roundToInt() / 100.0).toString() + "km 이동"
        binding.courseDetailCourseTimeTv.text = "${(homeCourseDetailResult.duration.toDouble() / 60).toInt()} 시간 소요"

        if (homeCourseDetailResult.followStatus == "FOLLOWING") {
            followStatus = "NOT_FOLLOW"
            binding.itemFollowFollowBtn.visibility = View.GONE
            binding.itemFollowFollowingBtn.visibility = View.VISIBLE
        } else {
            followStatus = "FOLLOWING"
            binding.itemFollowFollowBtn.visibility = View.VISIBLE
            binding.itemFollowFollowingBtn.visibility = View.GONE
        }

        if (homeCourseDetailResult.checkWriter) {
            binding.itemFollowFollowBtn.visibility = View.GONE
            binding.itemFollowFollowingBtn.visibility = View.GONE
        }

        binding.courseDetailFollowerTv.text = "팔로워 " + homeCourseDetailResult.follower.toString()
        binding.courseDetailFollowingTv.text = "팔로잉 " + homeCourseDetailResult.following.toString()

        binding.courseDetailCourseHeartNumber.text = homeCourseDetailResult.scrapCount.toString()
        binding.courseDetailCourseWriteDate.text = homeCourseDetailResult.startDate
        binding.courseDetailCourseName.text = homeCourseDetailResult.title
        binding.courseDetailNicknameTv.text = homeCourseDetailResult.writer.nickname

        Glide.with(this).load(homeCourseDetailResult.writer.profileImgUrl).placeholder(R.drawable.ic_profile)
            .circleCrop().centerCrop().into(binding.courseDetailProfileIv)

        courseDetailCourseRVAdapter.addAllItems(homeCourseDetailResult.placeResponseDtos)

        for (i in 0 until homeCourseDetailResult.placeResponseDtos.size) {
            pointList.add(LatLng(homeCourseDetailResult.placeResponseDtos[i].latitude, homeCourseDetailResult.placeResponseDtos[i].longitude))
        }

        memberId = homeCourseDetailResult.writer.memberId
    }

    override fun homeCourseDetailFailureView() {
        Log.d("API-TEST", "디테일 실패")
    }

    override fun homeScrapAddAndCancelSuccessView() {
        like = !like
        if (like) {
            binding.courseDetailTitleHeartIv.setImageResource(R.drawable.ic_heart_color)
            binding.courseDetailCourseHeartNumber.text = (binding.courseDetailCourseHeartNumber.toString().toInt() + 1).toString()
        } else {
            binding.courseDetailCourseHeartNumber.text = (binding.courseDetailCourseHeartNumber.toString().toInt() - 1).toString()
            binding.courseDetailTitleHeartIv.setImageResource(R.drawable.ic_course_detail_heart)
        }
    }

    override fun homeScrapAddAndCancelFailureView() {
        Toast.makeText(this, "스크랩에 실패했습니다", Toast.LENGTH_LONG).show()
    }

    override fun followUnfollowSuccessView() {
        if (followStatus == "NOT_FOLLOW") {
            followStatus = "FOLLOWING"
            binding.itemFollowFollowBtn.visibility = View.VISIBLE
            binding.itemFollowFollowingBtn.visibility = View.GONE
        } else {
            followStatus = "NOT_FOLLOW"
            binding.itemFollowFollowBtn.visibility = View.GONE
            binding.itemFollowFollowingBtn.visibility = View.VISIBLE
        }
    }

    override fun followUnfollowFailureView() {
        Toast.makeText(this, "팔로우/언팔로우 실패", Toast.LENGTH_LONG).show()
    }

    override fun scrapOnOffSuccessView() {
        like = !like
        binding.courseDetailTitleHeartIv.setImageResource(R.drawable.ic_heart)
    }

    override fun scrapOnOffFailureView() {

    }
}