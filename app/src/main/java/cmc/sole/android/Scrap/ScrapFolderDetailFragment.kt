package cmc.sole.android.Scrap

import android.content.Intent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MainActivity
import cmc.sole.android.MyCourse.MyCourseCourseRVAdapter
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentScrapFolderDetailBinding

class ScrapFolderDetailFragment: BaseFragment<FragmentScrapFolderDetailBinding>(FragmentScrapFolderDetailBinding::inflate) {

    private lateinit var scrapFolderDetailRVAdapter: MyCourseCourseRVAdapter
    private var courseList = ArrayList<DefaultCourse>()

    private lateinit var callback: OnBackPressedCallback

    override fun initAfterBinding() {
        binding.scrapFolderDetailTitle.text = arguments?.getString("title")

        initAdapter()
        initClickListener()


    }

    private fun initAdapter() {
        scrapFolderDetailRVAdapter = MyCourseCourseRVAdapter(courseList)
        binding.scrapFolderDetailCourseRv.adapter = scrapFolderDetailRVAdapter
        binding.scrapFolderDetailCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        scrapFolderDetailRVAdapter.setOnItemClickListener(object: MyCourseCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int) {
                startActivity(Intent(activity, CourseDetailActivity::class.java))
            }
        })
        scrapFolderDetailRVAdapter.setOnItemLongClickListener(object: MyCourseCourseRVAdapter.OnItemLongClickListener {
            override fun onItemLongClick(data: DefaultCourse, position: Int) {
                binding.scrapFolderDetailOptionIv.visibility = View.GONE
                binding.scrapFolderDetailOkTv.visibility = View.VISIBLE
                binding.scrapFolderDetailEditCv.visibility = View.GONE
                binding.scrapFolderDetailMoveCv.visibility = View.VISIBLE
                binding.scrapFolderDetailDeleteCv.visibility = View.VISIBLE
            }
        })

        courseList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
        courseList.add(DefaultCourse("img", "발리 다녀와서 파이", true, "서울 마포구", "4시간", "247m", arrayListOf("test"), null))
        courseList.add(DefaultCourse("img", "관람차로 내다보는 속초 바다", true, "강원 속초시", "30분", "91m", arrayListOf("test"), null))
        courseList.add(DefaultCourse("img", "행궁동 로컬 추천 코스", true, "경기 수원시", "3시간", "406m", arrayListOf("test"), null))
        courseList.add(DefaultCourse("img", "물고기, 고기", true, "제주도 서귀포", "5시간", "701m", arrayListOf("test"), null))
    }

    private fun initClickListener() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.scrapFolderDetailOptionIv.visibility == View.GONE) {
                    binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                    binding.scrapFolderDetailOkTv.visibility = View.GONE
                    binding.scrapFolderDetailEditCv.visibility = View.VISIBLE
                    binding.scrapFolderDetailMoveCv.visibility = View.GONE
                    binding.scrapFolderDetailDeleteCv.visibility = View.GONE
                } else if (binding.scrapFolderDetailOptionIv.visibility == View.VISIBLE) {
                    clearBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        binding.scrapFolderDetailEditCv.setOnClickListener {
            var scrapFolderOptionBottomFragment = ScrapFolderOptionBottomFragment()
            scrapFolderOptionBottomFragment.show(requireActivity().supportFragmentManager, "ScrapFolderDetailBottom")
        }

        binding.scrapFolderDetailDeleteCv.setOnClickListener {
            val scrapCourseDeleteDialog = DialogScrapCourseDelete()
            scrapCourseDeleteDialog.show(requireActivity().supportFragmentManager, "ScrapCourseDialog")
        }
    }

    fun clearBackStack() {
        val fragmentManager: FragmentManager = (context as MainActivity).supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}