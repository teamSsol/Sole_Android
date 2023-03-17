package cmc.sole.android.MyCourse

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentMyCourseBinding

class MyCourseFragment: BaseFragment<FragmentMyCourseBinding>(FragmentMyCourseBinding::inflate) {

    lateinit var myCourseCourseRVAdapter: MyCourseCourseRVAdapter
    var myCourseCourseList = ArrayList<DefaultCourse>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        myCourseCourseRVAdapter = MyCourseCourseRVAdapter(myCourseCourseList)
        binding.myCourseCourseRv.adapter = myCourseCourseRVAdapter
        binding.myCourseCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.myCourseCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 20))

        // MEMO: DUMMY DATA
        myCourseCourseList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
        myCourseCourseList.add(DefaultCourse("img", "발리 다녀와서 파이", true, "서울 마포구", "4시간", "247m", arrayListOf("test"), null))
        myCourseCourseList.add(DefaultCourse("img", "관람차로 내다보는 속초 바다", true, "강원 속초시", "30분", "91m", arrayListOf("test"), null))
        myCourseCourseList.add(DefaultCourse("img", "행궁동 로컬 추천 코스", true, "경기 수원시", "3시간", "406m", arrayListOf("test"), null))
        myCourseCourseList.add(DefaultCourse("img", "물고기, 고기", true, "제주도 서귀포", "5시간", "701m", arrayListOf("test"), null))
    }

    private fun initClickListener() {
        binding.myCourseLocationTag.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            myCourseTagBottomFragment.show(requireActivity().supportFragmentManager, "myCourseTagBottom")
        }

        binding.myCourseTransportTag.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            myCourseTagBottomFragment.show(requireActivity().supportFragmentManager, "myCourseTagBottom")
        }

        binding.myCourseWithTag.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            myCourseTagBottomFragment.show(requireActivity().supportFragmentManager, "myCourseTagBottom")
        }

        binding.myCourseFb.setOnClickListener {
            startActivity(Intent(activity, MyCourseWriteActivity::class.java))
        }
    }
}