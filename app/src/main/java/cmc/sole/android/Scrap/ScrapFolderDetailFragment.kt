package cmc.sole.android.Scrap

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.MainActivity
import cmc.sole.android.MyCourse.ScrapCourseRVAdapter
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.Scrap.Retrofit.ScrapCourseView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.Utils.BaseFragment
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
        scrapFolderId = arguments!!.getInt("scrapFolderId", 0)

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
        scrapFolderDetailRVAdapter.setOnItemClickListener(object: ScrapCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: ScrapCourseResult, position: Int) {
                startActivity(Intent(activity, CourseDetailActivity::class.java))
            }
        })
        scrapFolderDetailRVAdapter.setOnItemLongClickListener(object: ScrapCourseRVAdapter.OnItemLongClickListener {
            override fun onItemLongClick(data: ScrapCourseResult, position: Int) {
                binding.scrapFolderDetailOptionIv.visibility = View.GONE
                binding.scrapFolderDetailOkTv.visibility = View.VISIBLE
                binding.scrapFolderDetailEditCv.visibility = View.GONE
                binding.scrapFolderDetailMoveCv.visibility = View.VISIBLE
                binding.scrapFolderDetailDeleteCv.visibility = View.VISIBLE

                if (!deleteCourseId.contains(position))
                    deleteCourseId.add(position)
                else deleteCourseId.remove(position)

                Log.d("API-TEST", "deleteCourseId = $deleteCourseId")

//                if (dataList[0].viewType == 2) {
//                    for (i in 0 until dataList.size) {
//                        Log.d("API-TEST", "scrapFolderList 1 = $dataList")
//                        scrapFolderDetailRVAdapter.addItem(
//                            ScrapCourseResult(dataList[i].address, dataList[i].categories,
//                                dataList[i].courseId, dataList[i].distance, dataList[i].duration,
//                                dataList[i].like, dataList[i].thumbnailImg, dataList[i].title, 1))
//                        Log.d("API-TEST", "scrapFolderList 2 = $dataList")
//                        scrapFolderDetailRVAdapter.removeItem(0)
//                    }
////                    for (i in 0 until dataList.size - 1) {
////                        Log.d("API-TEST", "scrapFolderList 3 = $dataList")
////                        scrapFolderDetailRVAdapter.removeItem(i)
////                        Log.d("API-TEST", "scrapFolderList 4 = $dataList")
////                    }
//                }
                // scrapFolderDetailRVAdapter.get
//                binding.scrapFolderDetailOptionIv.visibility = View.GONE
//                binding.scrapFolderDetailOkTv.visibility = View.VISIBLE
//                binding.scrapFolderDetailEditCv.visibility = View.GONE
//                binding.scrapFolderDetailMoveCv.visibility = View.VISIBLE
//                binding.scrapFolderDetailDeleteCv.visibility = View.VISIBLE
                // binding.scrapFolderDetailCourseRv.
            }
        })

//        courseList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
//        courseList.add(DefaultCourse("img", "발리 다녀와서 파이", true, "서울 마포구", "4시간", "247m", arrayListOf("test"), null))
//        courseList.add(DefaultCourse("img", "관람차로 내다보는 속초 바다", true, "강원 속초시", "30분", "91m", arrayListOf("test"), null))
//        courseList.add(DefaultCourse("img", "행궁동 로컬 추천 코스", true, "경기 수원시", "3시간", "406m", arrayListOf("test"), null))
//        courseList.add(DefaultCourse("img", "물고기, 고기", true, "제주도 서귀포", "5시간", "701m", arrayListOf("test"), null))
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

    override fun scrapCourseSuccessView(scrapCourseResult: ArrayList<ScrapCourseResult>) {
        scrapFolderDetailRVAdapter.addAllItems(scrapCourseResult)

        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "1"))
        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "2"))
        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "4"))
        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "5"))

//        for (i in 0 until scrapCourseResult.size - 1)
//            scrapFolderDetailRVAdapter.addItem(
//                ScrapCourseResult(scrapCourseResult[i].address, scrapCourseResult[i].categories,
//                    scrapCourseResult[i].courseId, scrapCourseResult[i].distance, scrapCourseResult[i].duration,
//                    scrapCourseResult[i].like, scrapCourseResult[i].thumbnailImg, scrapCourseResult[i].title, 2))
//
//        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "1", 2))
//        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "2", 2))
//        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "3", 2))
//        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "4", 2))
//        scrapFolderDetailRVAdapter.addItem(ScrapCourseResult("", setOf(), 0, 0, 0, false, "", "5", 2))
    }

    override fun scrapCourseFailureView() {
        showToast("코스 불러오기 실패")
    }
}