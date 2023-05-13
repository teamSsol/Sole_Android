package cmc.sole.android.Scrap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MainActivity
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.Scrap.Retrofit.ScrapCourseView
import cmc.sole.android.Scrap.Retrofit.ScrapDefaultFolderView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentScrapFolderDetailBinding

class ScrapFolderDetailFragment: BaseFragment<FragmentScrapFolderDetailBinding>(FragmentScrapFolderDetailBinding::inflate),
    ScrapCourseView, ScrapDefaultFolderView {

    lateinit var scrapService: ScrapService

    private lateinit var scrapFolderDetailRVAdapter: ScrapCourseRVAdapter
    private var courseList = ArrayList<ScrapCourseResult>()
    lateinit var scrapFolderName: String
    var scrapFolderId = 0
    var deleteCourseId = ArrayList<Int>()

    private lateinit var callback: OnBackPressedCallback

    override fun initAfterBinding() {
        scrapFolderName = arguments?.getString("title").toString()
        binding.scrapFolderDetailTitle.text = arguments?.getString("title")
        scrapFolderId = requireArguments().getInt("scrapFolderId", -1)

        initService()
        initAdapter()

        if (scrapFolderName == "기본 폴더") {
            binding.scrapFolderDetailOptionIv.visibility = View.GONE
            scrapService.getDefaultFolder()
        } else scrapService.getScrapCourse(scrapFolderId)

        initClickListener()
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapCourseView(this)
        scrapService.setScrapDefaultFolderView(this)
    }

    private fun initAdapter() {
        scrapFolderDetailRVAdapter = ScrapCourseRVAdapter(courseList)
        binding.scrapFolderDetailCourseRv.adapter = scrapFolderDetailRVAdapter
        binding.scrapFolderDetailCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.scrapFolderDetailCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        scrapFolderDetailRVAdapter.setOnItemClickListener(object: ScrapCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: ScrapCourseResult, position: Int) {
                if (binding.scrapFolderDetailEditCv.visibility == View.VISIBLE) {
                    scrapFolderDetailRVAdapter.checkMode(0)
                    val intent = Intent(activity, CourseDetailActivity::class.java)
                    intent.putExtra("courseId", data.courseId)
                    startActivity(intent)
                } else {
                    scrapFolderDetailRVAdapter.checkMode(1)
                    if (!data.isChecked) {
                        deleteCourseId.add(data.courseId)
                    } else {
                        deleteCourseId.remove(data.courseId)
                    }
                }
            }
        })
    }

    private fun initClickListener() {
        binding.scrapFolderDetailBackIv.setOnClickListener {
            clearBackStack()
        }

        // MEMO: 뒤로가기 눌렀을 때
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.scrapFolderDetailEditCv.visibility == View.GONE) {
                    binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                    defaultMode()
                } else {
                    clearBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        binding.scrapFolderDetailOptionIv.setOnClickListener {
            var scrapFolderOptionBottomFragment = ScrapFolderOptionBottomFragment()
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            bundle.putIntegerArrayList("deleteCourseId", deleteCourseId)
            scrapFolderOptionBottomFragment.arguments = bundle
            scrapFolderOptionBottomFragment.show(requireActivity().supportFragmentManager, "ScrapFolderDetailBottom")
            scrapFolderOptionBottomFragment.setOnFinishListener(object: ScrapFolderOptionBottomFragment.OnScrapOptionFinishListener {
                override fun finish(mode: String, newFolderName: String?) {
                    if (mode == "delete") {
                        clearBackStack()
                    } else if (mode == "edit") binding.scrapFolderDetailTitle.text = newFolderName
                }
            })
        }

        binding.scrapFolderDetailEditCv.setOnClickListener {
            editMode()
        }

        binding.scrapFolderDetailMoveCv.setOnClickListener {
            val scrapCourseMoveDialog = DialogScrapCourseMove()
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            bundle.putIntegerArrayList("courseId", deleteCourseId)
            bundle.putString("scrapFolderName", scrapFolderName)
            scrapCourseMoveDialog.arguments = bundle
            scrapCourseMoveDialog.show(requireActivity().supportFragmentManager, "ScrapCourseMoveDialog")
        }

        binding.scrapFolderDetailDeleteCv.setOnClickListener {
            val scrapCourseDeleteDialog = DialogScrapCourseDelete()
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            bundle.putIntegerArrayList("courseId", deleteCourseId)
            bundle.putString("scrapFolderName", scrapFolderName)
            scrapCourseDeleteDialog.arguments = bundle
            scrapCourseDeleteDialog.show(requireActivity().supportFragmentManager, "ScrapCourseDeleteDialog")
            scrapCourseDeleteDialog.setOnFinishListener(object: DialogScrapCourseDelete.OnFinishListener {
                override fun finish() {
                    binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                    defaultMode()
                }
            })
        }

        binding.scrapFolderDetailOkTv.setOnClickListener {
            if (binding.scrapFolderDetailOptionIv.visibility == View.GONE) {
                defaultMode()
            }
        }
    }

    fun clearBackStack() {
        val fragmentManager: FragmentManager = (context as MainActivity).supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun editMode() {
        binding.scrapFolderDetailOptionIv.visibility = View.GONE
        binding.scrapFolderDetailEditCv.visibility = View.GONE
        binding.scrapFolderDetailOkTv.visibility = View.VISIBLE
        binding.scrapFolderDetailMoveCv.visibility = View.VISIBLE
        binding.scrapFolderDetailDeleteCv.visibility = View.VISIBLE

        for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
            scrapFolderDetailRVAdapter.getItems(i).checkMode = true
            scrapFolderDetailRVAdapter.notifyItemChanged(i)
        }
    }

    fun defaultMode() {
        binding.scrapFolderDetailEditCv.visibility = View.VISIBLE
        binding.scrapFolderDetailOkTv.visibility = View.GONE
        binding.scrapFolderDetailMoveCv.visibility = View.GONE
        binding.scrapFolderDetailDeleteCv.visibility = View.GONE

        for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
            scrapFolderDetailRVAdapter.getItems(i).checkMode = false
            scrapFolderDetailRVAdapter.getItems(i).isChecked = false
            scrapFolderDetailRVAdapter.notifyItemChanged(i)
        }
    }

    override fun scrapCourseSuccessView(scrapCourseResult: ArrayList<ScrapCourseResult>) {
        if (scrapCourseResult.size == 0) {
            binding.scrapFolderDetailLayout.visibility = View.VISIBLE
            binding.scrapFolderDetailEditCv.isEnabled = false
        } else {
            binding.scrapFolderDetailLayout.visibility = View.GONE
            binding.scrapFolderDetailEditCv.isEnabled = true
            scrapFolderDetailRVAdapter.addAllItems(scrapCourseResult)
        }
    }

    override fun scrapCourseFailureView() {
        showToast("코스 불러오기 실패")
    }

    override fun scrapDefaultFolderSuccessView(scrapDefaultFolderList: ArrayList<ScrapCourseResult>) {
        if (scrapDefaultFolderList.size == 0) {
            binding.scrapFolderDetailLayout.visibility = View.VISIBLE
            binding.scrapFolderDetailEditCv.isEnabled = false
        } else {
            binding.scrapFolderDetailLayout.visibility = View.GONE
            binding.scrapFolderDetailEditCv.isEnabled = true
            scrapFolderDetailRVAdapter.addAllItems(scrapDefaultFolderList)
        }
    }

    override fun scrapDefaultFolderFailureView() {
        showToast("코스 불러오기 실패")
    }
}