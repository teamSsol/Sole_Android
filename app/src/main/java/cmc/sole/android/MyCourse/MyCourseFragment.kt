package cmc.sole.android.MyCourse

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryResult
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryView
import cmc.sole.android.MyCourse.Retrofit.MyCourseService
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.TagTranslator
import cmc.sole.android.databinding.FragmentMyCourseBinding
import com.bumptech.glide.Glide

class MyCourseFragment: BaseFragment<FragmentMyCourseBinding>(FragmentMyCourseBinding::inflate),
    MyCourseHistoryView {

    lateinit var myCourseCourseRVAdapter: MyCourseCourseRVAdapter
    var myCourseCourseList = ArrayList<DefaultCourse>()
    lateinit var myCourseService: MyCourseService

    override fun initAfterBinding() {
        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        myCourseService = MyCourseService()
        myCourseService.setMyCourseHistoryView(this)
        myCourseService.getMyCourseHistory()
    }

    private fun initAdapter() {
        myCourseCourseRVAdapter = MyCourseCourseRVAdapter(myCourseCourseList)
        binding.myCourseCourseRv.adapter = myCourseCourseRVAdapter
        binding.myCourseCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.myCourseCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 20))

        // MEMO: DUMMY DATA
//        myCourseCourseList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "발리 다녀와서 파이", true, "서울 마포구", "4시간", "247m", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "관람차로 내다보는 속초 바다", true, "강원 속초시", "30분", "91m", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "행궁동 로컬 추천 코스", true, "경기 수원시", "3시간", "406m", arrayListOf("test"), null))
//        myCourseCourseList.add(DefaultCourse("img", "물고기, 고기", true, "제주도 서귀포", "5시간", "701m", arrayListOf("test"), null))
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

    override fun setMyCourseHistorySuccessView(myCourseHistoryResult: MyCourseHistoryResult) {
        // UPDATE: API 프로필 이미지 추가하면 추가해주기!
        // Glide.with(this).load(myCourseHistoryResult.profileImg)
        binding.myCourseNicknameTv.text = myCourseHistoryResult.nickname
        // UPDATE: Text Span 처리 필요
        binding.myCourseInfoContent.text = "지금까지 ${myCourseHistoryResult.totalDate}일간 ${myCourseHistoryResult.totalPlaces}곳의 장소를 방문하며,\n이번 달 총 ${myCourseHistoryResult.totalCourses}개의 코스를 기록했어요"
        binding.myCourseInfoTag.text = "가장 많이 방문한 지역은 ${TagTranslator.tagTranslate(requireActivity() as AppCompatActivity, myCourseHistoryResult.mostRegion)}이고\n" +
                "${TagTranslator.tagTranslate(requireActivity() as AppCompatActivity, myCourseHistoryResult.mostTransCategories.elementAt(0))} 이동해서" +
                "${TagTranslator.tagTranslate(requireActivity() as AppCompatActivity, myCourseHistoryResult.mostPlaceCategories.elementAt(0))}을 다녔어요."
    }

    override fun setMyCourseHistoryFailureView() {

    }
}