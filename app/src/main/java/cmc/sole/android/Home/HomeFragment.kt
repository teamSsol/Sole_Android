package cmc.sole.android.Home

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.MyPage.MyPageActivity
import cmc.sole.android.Home.Retrofit.HomeDefaultCourseView
import cmc.sole.android.Home.Retrofit.HomePopularCourseView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Search.SearchActivity
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    HomePopularCourseView, HomeDefaultCourseView {

    private lateinit var popularCourseRVAdapter: HomePopularCourseRVAdapter
    private lateinit var myCourseRVAdapter: HomeDefaultCourseRVAdapter
    private var popularCourseList = ArrayList<PopularCourse>()
    private var myCourseList = ArrayList<DefaultCourse>()

    private lateinit var homeService: HomeService
    // MEMO: 원하는 만큼 수정
    var courseId = 5

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
        initAPIService()
    }

    private fun initAdapter() {
        popularCourseRVAdapter = HomePopularCourseRVAdapter(popularCourseList)
        binding.homePopularCourseRv.adapter = popularCourseRVAdapter
        binding.homePopularCourseRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.homePopularCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popularCourseRVAdapter.setOnItemClickListener(object: HomePopularCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: PopularCourse, position: Int) {
                startActivity(Intent(activity, CourseDetailActivity::class.java))
            }
        })

        myCourseRVAdapter = HomeDefaultCourseRVAdapter(myCourseList)
        binding.homeMyCourseRv.adapter = myCourseRVAdapter
        binding.homeMyCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        binding.homeMyCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myCourseRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int) {
                startActivity(Intent(activity, CourseDetailActivity::class.java))
            }
        })

        // MEMO: DummyData
        // CONDITION: 이 주변 인기 코스 7개로 고정
        popularCourseList.add(PopularCourse(0, "베이커리 맞은 편 일식당", "테스트1"))
        popularCourseList.add(PopularCourse(1, "발리 다녀와서 파이", "테스트1"))
        popularCourseList.add(PopularCourse(2, "관람차로 내다보는 속초 바다", "테스트1"))

        // CONDITION: 내 취향 코스 5개 + 더 보기 버튼 이용
        myCourseList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
        myCourseList.add(DefaultCourse("img", "발리 다녀와서 파이", true, "서울 마포구", "4시간", "247m", arrayListOf("test"), null))
        myCourseList.add(DefaultCourse("img", "관람차로 내다보는 속초 바다", true, "강원 속초시", "30분", "91m", arrayListOf("test"), null))
        myCourseList.add(DefaultCourse("img", "행궁동 로컬 추천 코스", true, "경기 수원시", "3시간", "406m", arrayListOf("test"), null))
        myCourseList.add(DefaultCourse("img", "물고기, 고기", true, "제주도 서귀포", "5시간", "701m", arrayListOf("test"), null))
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
    }

    private fun initAPIService() {
        homeService = HomeService()
        homeService.setHomePopularCourseView(this)
        homeService.setHomeDefaultCourseView(this)
        homeService.getHomePopularCourse()
        homeService.getHomeDefaultCourse(courseId, "")
    }

    override fun homePopularCourseSuccessView(homePopularResponse: HomePopularResponse) {

    }

    override fun homePopularCourseFailureView() {

    }

    override fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {

    }

    override fun homeDefaultCourseFailureView() {

    }
}