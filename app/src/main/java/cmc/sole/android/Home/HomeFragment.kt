package cmc.sole.android.Home

import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    lateinit var popularCourseRVAdapter: HomePopularCourseRVAdapter
    lateinit var myCourseRVAdapter: HomeMyCourseRVAdapter
    private var popularCourseList = ArrayList<PopularCourse>()
    private var myCourseList = ArrayList<MyCourse>()

    override fun initAfterBinding() {
        initAdapter()
    }

    private fun initAdapter() {
        popularCourseRVAdapter = HomePopularCourseRVAdapter(popularCourseList)
        binding.homePopularCourseRv.adapter = popularCourseRVAdapter
        binding.homePopularCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        myCourseRVAdapter = HomeMyCourseRVAdapter(myCourseList)
        binding.homeMyCourseRv.adapter = myCourseRVAdapter
        binding.homeMyCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // MEMO: DummyData
        popularCourseList.add(PopularCourse("image", "테스트1"))
        popularCourseList.add(PopularCourse("image", "테스트2"))
        popularCourseList.add(PopularCourse("image", "테스트3"))
        popularCourseList.add(PopularCourse("image", "테스트4"))
        popularCourseList.add(PopularCourse("image", "테스트5"))

        myCourseList.add(MyCourse("image", "제목제목", false, "서울 종로구1", "1시간 소요", "1.2km", arrayListOf("테스트")))
        myCourseList.add(MyCourse("image", "제목제목", true, "서울 종로구2", "2시간 소요", "2.2km", arrayListOf("테스트")))
        myCourseList.add(MyCourse("image", "제목제목", false, "서울 종로구3", "3시간 소요", "3.2km", arrayListOf("테스트")))
        myCourseList.add(MyCourse("image", "제목제목", true, "서울 종로구4", "4시간 소요", "4.2km", arrayListOf("테스트")))
        myCourseList.add(MyCourse("image", "제목제목", true, "서울 종로구5", "5시간 소요", "5.2km", arrayListOf("테스트")))
    }
}