package cmc.sole.android.MyCourse

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.Utils.BaseFragment
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

        // MEMO: DUMMY DATA
//        myCourseCourseList.add(DefaultCourse("img", "코스1 이름", false, "위치1", "시간1", "거리", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "코스2 이름", true, "위치2", "시간2", "거리", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "코스3 이름", true, "위치3", "시간3", "거리", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "코스4 이름", true, "위치4", "시간4", "거리", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "코스5 이름", true, "위치5", "시간5", "거리", arrayListOf("test"), null))
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