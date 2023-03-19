package cmc.sole.android.Scrap

import android.content.Intent
import android.os.Bundle
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
        scrapFolderId = requireArguments().getInt("scrapFolderId", -1)

        Log.d("API-TEST", "scrapFolderId = $scrapFolderId")

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
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.scrapFolderDetailOptionIv.visibility == View.GONE) {
                    binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                    binding.scrapFolderDetailEditCv.visibility = View.VISIBLE
                    binding.scrapFolderDetailOkTv.visibility = View.GONE
                    binding.scrapFolderDetailMoveCv.visibility = View.GONE
                    binding.scrapFolderDetailDeleteCv.visibility = View.GONE

                    for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
                        scrapFolderDetailRVAdapter.getItems(i).checkMode = false
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
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            scrapFolderOptionBottomFragment.arguments = bundle
            scrapFolderOptionBottomFragment.show(requireActivity().supportFragmentManager, "ScrapFolderDetailBottom")
        }

        binding.scrapFolderDetailEditCv.setOnClickListener {
            binding.scrapFolderDetailOptionIv.visibility = View.GONE
            binding.scrapFolderDetailEditCv.visibility = View.GONE
            binding.scrapFolderDetailOkTv.visibility = View.VISIBLE
            binding.scrapFolderDetailMoveCv.visibility = View.VISIBLE
            binding.scrapFolderDetailDeleteCv.visibility = View.VISIBLE

            for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
                scrapFolderDetailRVAdapter.getItems(i).checkMode = true
            }
            scrapFolderDetailRVAdapter.notifyDataSetChanged()
        }

        binding.scrapFolderDetailDeleteCv.setOnClickListener {
            val scrapCourseDeleteDialog = DialogScrapCourseDelete()
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            bundle.putIntegerArrayList("courseId", deleteCourseId)
            scrapCourseDeleteDialog.arguments = bundle
            scrapCourseDeleteDialog.show(requireActivity().supportFragmentManager, "ScrapCourseDialog")
            scrapCourseDeleteDialog.setOnFinishListener(object: DialogScrapCourseDelete.OnFinishListener {
                override fun finish() {
                    binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                    binding.scrapFolderDetailEditCv.visibility = View.VISIBLE
                    binding.scrapFolderDetailOkTv.visibility = View.GONE
                    binding.scrapFolderDetailMoveCv.visibility = View.GONE
                    binding.scrapFolderDetailDeleteCv.visibility = View.GONE

                    for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
                        scrapFolderDetailRVAdapter.getItems(i).checkMode = false
                        scrapFolderDetailRVAdapter.getItems(i).isChecked = false
                    }
                    scrapFolderDetailRVAdapter.notifyDataSetChanged()
                }
            })
        }

        binding.scrapFolderDetailOkTv.setOnClickListener {
            if (binding.scrapFolderDetailOptionIv.visibility == View.GONE) {
                binding.scrapFolderDetailOptionIv.visibility = View.VISIBLE
                binding.scrapFolderDetailEditCv.visibility = View.VISIBLE
                binding.scrapFolderDetailOkTv.visibility = View.GONE
                binding.scrapFolderDetailMoveCv.visibility = View.GONE
                binding.scrapFolderDetailDeleteCv.visibility = View.GONE

                for (i in 0 until scrapFolderDetailRVAdapter.getAllItems().size) {
                    scrapFolderDetailRVAdapter.getItems(i).checkMode = false
                }
                scrapFolderDetailRVAdapter.notifyDataSetChanged()
            }
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