package cmc.sole.android.Scrap

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.MainActivity
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.Scrap.Retrofit.ScrapCourseView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentScrapFolderDetailBinding

class ScrapFolderDetailFragment: BaseFragment<FragmentScrapFolderDetailBinding>(FragmentScrapFolderDetailBinding::inflate),
    ScrapCourseView {

    lateinit var scrapService: ScrapService

    private lateinit var scrapFolderDetailRVAdapter: ScrapCourseRVAdapter
    private var courseList = ArrayList<ScrapCourseResult>()
    var scrapFolderId = 0
    var deleteCourseId = ArrayList<Int>()

    private lateinit var callback: OnBackPressedCallback

    override fun initAfterBinding() {
        binding.scrapFolderDetailTitle.text = arguments?.getString("title")
        scrapFolderId = requireArguments().getInt("scrapFolderId", 0)

        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapCourseView(this)
        scrapService.getScrapCourse(scrapFolderId)
    }

    private fun initAdapter() {
        scrapFolderDetailRVAdapter = ScrapCourseRVAdapter(courseList)
        binding.scrapFolderDetailCourseRv.adapter = scrapFolderDetailRVAdapter
        binding.scrapFolderDetailCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.scrapFolderDetailCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        if (binding.scrapFolderDetailEditCv.visibility == View.VISIBLE) {
            showLog("API-TEST", "기본 모드")
            scrapFolderDetailRVAdapter.setOnItemClickListener(object: ScrapCourseRVAdapter.OnItemClickListener {
                override fun onItemClick(data: ScrapCourseResult, position: Int) {
                    if (binding.scrapFolderDetailEditCv.visibility == View.VISIBLE) {
                        scrapFolderDetailRVAdapter.test(0)
                    } else {
                        scrapFolderDetailRVAdapter.test(1)
                    }
//                    if (binding.scrapFolderDetailEditCv.visibility == View.VISIBLE) {
//                        startActivity(Intent(activity, CourseDetailActivity::class.java))
//                    }
//                    if (binding.scrapFolderDetailEditCv.visibility == View.VISIBLE) {
//
//                    }
                }
            })
        } else {
            showLog("API-TEST", "편집 모드")
            scrapFolderDetailRVAdapter.setOnItemClickListener(object: ScrapCourseRVAdapter.OnItemClickListener {
                override fun onItemClick(data: ScrapCourseResult, position: Int) {
                    if (binding.scrapFolderDetailEditCv.visibility == View.VISIBLE) {
                        // startActivity(Intent(activity, CourseDetailActivity::class.java))
                    }
                }
            })
        }
    }

    private fun initClickListener() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.scrapFolderDetailOptionIv.visibility == View.GONE) {
                    binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                    binding.scrapFolderDetailEditCv.visibility = View.VISIBLE
                    binding.scrapFolderDetailOkTv.visibility = View.GONE
                    binding.scrapFolderDetailMoveCv.visibility = View.GONE
                    binding.scrapFolderDetailDeleteCv.visibility = View.GONE

                    for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
                        scrapFolderDetailRVAdapter.getItems(i).isChecked = false
                    }
                    scrapFolderDetailRVAdapter.notifyDataSetChanged()
                } else {
                    clearBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        binding.scrapFolderDetailOptionIv.setOnClickListener {
            var scrapFolderOptionBottomFragment = ScrapFolderOptionBottomFragment()
            scrapFolderOptionBottomFragment.show(requireActivity().supportFragmentManager, "ScrapFolderDetailBottom")
        }

        binding.scrapFolderDetailEditCv.setOnClickListener {
            showLog("API-TEST", "edit click")
            binding.scrapFolderDetailOptionIv.visibility = View.GONE
            binding.scrapFolderDetailEditCv.visibility = View.GONE
            binding.scrapFolderDetailOkTv.visibility = View.VISIBLE
            binding.scrapFolderDetailMoveCv.visibility = View.VISIBLE
            binding.scrapFolderDetailDeleteCv.visibility = View.VISIBLE

            for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
                scrapFolderDetailRVAdapter.getItems(i).isChecked = true
            }
            scrapFolderDetailRVAdapter.notifyDataSetChanged()
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

    override fun scrapCourseSuccessView(scrapCourseResult: ArrayList<ScrapCourseResult>) {
        // scrapFolderDetailRVAdapter.addAllItems(scrapCourseResult)

        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "1"))
        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "2"))
        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "3"))
        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "4"))
    }

    override fun scrapCourseFailureView() {
        showToast("코스 불러오기 실패")
    }
}