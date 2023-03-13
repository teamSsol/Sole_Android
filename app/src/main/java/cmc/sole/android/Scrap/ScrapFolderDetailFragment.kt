package cmc.sole.android.Scrap

import android.content.Intent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.CourseDetailActivity
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

        courseList.add(DefaultCourse("image", "제목제목", false, "서울 종로구1", "1시간 소요", "1.2km", arrayListOf("테스트")))
        courseList.add(DefaultCourse("image", "제목제목", true, "서울 종로구2", "2시간 소요", "2.2km", arrayListOf("테스트")))
        courseList.add(DefaultCourse("image", "제목제목", false, "서울 종로구3", "3시간 소요", "3.2km", arrayListOf("테스트")))
        courseList.add(DefaultCourse("image", "제목제목", true, "서울 종로구4", "4시간 소요", "4.2km", arrayListOf("테스트")))
        courseList.add(DefaultCourse("image", "제목제목", true, "서울 종로구5", "5시간 소요", "5.2km", arrayListOf("테스트")))
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
            scrapFolderOptionBottomFragment.show(
                requireActivity().supportFragmentManager,
                "ScrapFolderDetailBottom"
            )
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