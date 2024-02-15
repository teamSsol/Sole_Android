package cmc.sole.android.MyCourse

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MyCourse.Retrofit.*
import cmc.sole.android.Write.MyCourseWriteActivity
import cmc.sole.android.R

import cmc.sole.android.Utils.DynamicDrawableSpan
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.FragmentMyCourseBinding
import com.bumptech.glide.Glide
import androidx.fragment.app.Fragment
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.Utils.Region
import cmc.sole.android.Utils.returnRegionCode


class MyCourseFragment: Fragment(),
    MyCourseHistoryInfoView, MyCourseHistoryView, MyCourseNullTagHistoryView {

    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!

    lateinit var myCourseCourseRVAdapter: MyCourseCourseRVAdapter
    var myCourseCourseList = ArrayList<DefaultCourse>()
    lateinit var myCourseService: MyCourseService
    var courseId: Int? = null
    var detailCourseId = 0

    // MEMO: 취향 태그
    // private lateinit var tagRVAdapter: MyCourseTagRVAdapter
    // private var tagList = ArrayList<String>()
    var tagFlagList = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    var regionFlagList = arrayListOf<String>()
    var placeCategories = mutableSetOf<Categories>()
    var transCategories = mutableSetOf<Categories>()
    var withCategories = mutableSetOf<Categories>()

    override fun onResume() {
        super.onResume()
        myCourseService.getMyCourseNullTagHistory(courseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCourseBinding.inflate(inflater, container, false)

        initService()
        initAdapter()
        initClickListener()
        
        return binding.root
    }

    private fun initService() {
        myCourseService = MyCourseService()
        myCourseService.setMyCourseHistoryInfoView(this)
        myCourseService.setMyCourseHistoryView(this)
        myCourseService.setMyCourseNullTagView(this)
        myCourseService.getMyCourseHistoryInfo()
        myCourseService.getMyCourseNullTagHistory(courseId)
    }

    private fun initAdapter() {
        myCourseCourseRVAdapter = MyCourseCourseRVAdapter(myCourseCourseList)
        binding.myCourseCourseRv.adapter = myCourseCourseRVAdapter
        binding.myCourseCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.myCourseCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        myCourseCourseRVAdapter.setOnItemClickListener(object: MyCourseCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int) {
                // API: 코스 상세보기 연결
                detailCourseId = data.courseId
                var intent = Intent(activity, CourseDetailActivity::class.java)
                intent.putExtra("courseId", detailCourseId)
                startActivity(intent)
            }
        })
    }

    private fun initClickListener() {
        binding.myCourseFilterCv.setOnClickListener {
            // MEMO: 검색 필터 복붙
            val myCourseOptionBottomFragment = MyCourseOptionBottomFragment()
            var bundle = Bundle()
            bundle.putString("viewFlag", "myCourseFragment")
            bundle.putBooleanArray("tagFlag", tagFlagList)
            bundle.putStringArrayList("regionFlag", regionFlagList)
            myCourseOptionBottomFragment.arguments = bundle
            myCourseOptionBottomFragment.show(activity?.supportFragmentManager!!, "CourseDetailOptionBottom")
            myCourseOptionBottomFragment.setOnFinishListener(object: MyCourseOptionBottomFragment.OnTagFragmentFinishListener {
                override fun finish(returnTagList: List<TagButton>, returnRegionList: ArrayList<String>) {
                    // MEMO: 태그
                    for (i in 0..17) {
                        tagFlagList[i] = returnTagList[i].isChecked
                    }

                    var tagArrayList = arrayListOf<String>()
                    for (i in 0..17) {
                        if (tagFlagList[i]) tagArrayList.add(returnTagList[i].title)
                    }

                    tagArrayList.add("")
                    // tagRVAdapter.addAllItems(tagArrayList)

                    // MEMO: 지역 필터
                    regionFlagList = returnRegionList
                    var regionList = hashSetOf<Region>()
                    for (i in 0 until returnRegionList.size) {
                        regionList.add(returnRegionCode(returnRegionList[i]))
                    }

                    var region = regionList
                    var placeCategories = returnCategories("PLACE")
                    var withCategories = returnCategories("WITH")
                    var transCategories = returnCategories("TRANS")

                    if (region.size == 0 && placeCategories.size == 0 && withCategories.size == 0 && transCategories.size == 0) {
                        myCourseService.getMyCourseNullTagHistory(courseId)
                    } else {
                        myCourseService.getMyCourseHistory(null, MyCourseHistoryRequest(region, placeCategories, transCategories, withCategories))
                    }
                }
            })

            // MEMO: 기존에 있던 버전
//            myCourseOptionBottomFragment.setOnFinishListener(object: MyCourseOptionBottomFragment.OnTagFragmentFinishListener {
//                override fun finish(returnTagList: List<TagButton>, returnRegionList: ArrayList<String>) {
//                    var filterFlag = false
//
//                    // MEMO: 태그
//                    for (i in 0..17) {
//                        tagFlagList[i] = returnTagList[i].isChecked
//
//                        if (returnTagList[i].isChecked) {
//                            filterFlag = true
//                        }
//                    }
//
//                    var tagArrayList = arrayListOf<String>()
//                    for (i in 0..17) {
//                        if (tagFlagList[i]) tagArrayList.add(returnTagList[i].title)
//                    }
//
//                    tagArrayList.add("")
//                    // tagRVAdapter.addAllItems(tagArrayList)
//
//                    // MEMO: 지역 필터
//                    regionFlagList = returnRegionList
//                    var regionList = mutableSetOf<Region>()
//                    for (i in 0 until returnRegionList.size) {
//                        regionList.add(returnRegionCode(returnRegionList[i]))
//                    }
//                    if (returnRegionList.size > 0) {
//                        filterFlag = true
//                    }
//
//                    if (filterFlag) {
//                        binding.myCourseFilterCv.strokeColor = ContextCompat.getColor(binding.root.context, R.color.main)
//                    } else {
//                        binding.myCourseFilterCv.strokeColor = Color.parseColor("#D3D4D5")
//                    }
//
//                    var region = regionList
//                    var placeCategories = returnCategories("PLACE")
//                    var withCategories = returnCategories("WITH")
//                    var transCategories = returnCategories("TRANS")
//
//                    if (region.size == 0 && placeCategories.size == 0 && withCategories.size == 0 && transCategories.size == 0) {
//                        myCourseService.getMyCourseNullTagHistory(courseId)
//                    } else {
//                        myCourseService.getMyCourseHistory(null, MyCourseHistoryRequest(region, placeCategories, transCategories, withCategories))
//                    }
//                }
//            })
        }

        binding.myCourseFb.setOnClickListener {
            startActivity(Intent(activity, MyCourseWriteActivity::class.java))
        }
    }

    private fun returnCategories(option: String): MutableSet<Categories> {
        var returnCategoriesArray = mutableSetOf<Categories>()

        when(option) {
            "PLACE" -> {
                for (i in 0..8) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1))
                    }
                }
            } "WITH" -> {
                for (i in 9..13) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1))
                    }
                }
            } else -> {
                for (i in 14..17) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1))
                    }
                }
            }
        }

        return returnCategoriesArray
    }

    override fun setMyCourseHistoryInfoSuccessView(myCourseHistoryResult: MyCourseHistoryInfoResult) {
        Glide.with(this).load(myCourseHistoryResult.profileImg).centerCrop().circleCrop().placeholder(R.drawable.ic_profile).into(binding.myCourseProfileIv)
        binding.myCourseNicknameTv.text = myCourseHistoryResult.nickname

        var totalDate = myCourseHistoryResult.totalDate
        var totalPlaces = myCourseHistoryResult.totalPlaces
        var totalCourses = myCourseHistoryResult.totalCourses
        var infoContent = "지금까지 $totalDate 일간 $totalPlaces 곳의 장소를 방문하며,\n이번 달 총 $totalCourses 개의 코스를 기록했어요"
        binding.myCourseInfoContent.text = setSpannableText(infoContent, "top")

        var mostRegion = myCourseHistoryResult.mostRegion

        var mostTransCategories = "."
        var mostPlaceCategories = "."
        if (myCourseHistoryResult.mostTransCategories.isNotEmpty())
            mostTransCategories = Translator.tagEngToKor(activity as AppCompatActivity, myCourseHistoryResult.mostTransCategories.elementAt(0))
        if (myCourseHistoryResult.mostTransCategories.isNotEmpty())
            mostPlaceCategories = Translator.tagEngToKor(activity as AppCompatActivity, myCourseHistoryResult.mostPlaceCategories.elementAt(0))

        if (mostRegion == "" || mostTransCategories == "." || mostPlaceCategories == ".") {
            binding.myCourseInfoTag1.visibility = View.GONE
            binding.myCourseInfoTag2.visibility = View.GONE
        } else {
            var tagContent1 = "가장 많이 방문한 지역은 $mostRegion 이고"
            binding.myCourseInfoTag1.text = setSpannableText(tagContent1, "bottom1")

            var tagContent2 = "$mostTransCategories 으로 이동해서 $mostPlaceCategories 을 다녔어요."
            binding.myCourseInfoTag2.text = setSpannableText(tagContent2, "bottom2")
        }
    }

    override fun setMyCourseHistoryInfoFailureView() {
        var message = "나의 기록 정보 가져오기 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun setMyCourseHistorySuccessView(myCourseHistoryResult: ArrayList<DefaultCourse>) {
        binding.myCourseFilterCv.strokeColor = ContextCompat.getColor(binding.root.context, R.color.main)
        myCourseCourseRVAdapter.addAllItems(myCourseHistoryResult)
    }

    override fun setMyCourseHistoryFailureView() {
        var message = "나의 기록 가져오기 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setSpannableText(text: String, option: String): SpannableStringBuilder {
        var textArray = text.split(" ")

        val builder = SpannableStringBuilder(text)

        when (option) {
            "top" -> {
                // MEMO: TextSpan 적용 -> [지금까지, ?, 일간, ?, 곳의, 장소를, 방문하며, 이번, 달, 총, ?, 개의, 코스를, 기록했어요]
                var dateTextLength = returnTextLength(textArray, 1, option)
                var placeTextLength = returnTextLength(textArray, 3, option)
                var courseTextLength = returnTextLength(textArray, 9, option)

                builder.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main)), dateTextLength[0], dateTextLength[1] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main)), placeTextLength[0], placeTextLength[1] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main)), courseTextLength[0], courseTextLength[1] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                builder.setSpan(StyleSpan(Typeface.BOLD), dateTextLength[0], dateTextLength[1] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(StyleSpan(Typeface.BOLD), placeTextLength[0], placeTextLength[1] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(StyleSpan(Typeface.BOLD), courseTextLength[0], courseTextLength[1] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                return builder
            }
            "bottom1" -> {
                // MEMO: TextSpan 적용 -> [가장, 많이, 방문한, 지역은, ?, ?, 이고]
                var dateTextLength = returnTextLength(textArray, 5, option)
                builder.setSpan(DynamicDrawableSpan(requireContext(), Color.parseColor("#EDEDED"), Color.parseColor("#000000")), dateTextLength[0], dateTextLength[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                return builder
            }
            else -> {
                // MEMO: TextSpan 적용 -> [?, ?, 으로, 이동해서, ?, ?, 를, 다녔어요]
                var placeTextLength = returnTextLength(textArray, 1, option)
                var courseTextLength = returnTextLength(textArray, 5, option)

                builder.setSpan(DynamicDrawableSpan(requireContext(), Color.parseColor("#EDEDED"), Color.parseColor("#000000")), placeTextLength[0], placeTextLength[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(DynamicDrawableSpan(requireContext(), Color.parseColor("#EDEDED"), Color.parseColor("#000000")), courseTextLength[0], courseTextLength[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                return builder
            }
        }
    }

    private fun returnTextLength(text: List<String>, end: Int, option: String): ArrayList<Int> {
        var startLength = 0
        var totalLength = 0
        return if (option == "top") {
            for (i in 0..end) {
                totalLength += text[i].length
                if (i == end - 1) startLength = totalLength
            }
            arrayListOf(startLength + end, totalLength + end)
        } else {
            for (i in 0..end) {
                totalLength += text[i].length + 1
                if (i == end - 2) startLength = totalLength
            }

            arrayListOf(startLength, totalLength - 1)
        }
    }

    override fun setMyCourseNullTagHistorySuccessView(myCourseHistoryResult: ArrayList<DefaultCourse>) {
        binding.myCourseFilterCv.strokeColor = Color.parseColor("#D3D4D5")
        myCourseCourseRVAdapter.addAllItems(myCourseHistoryResult)
    }

    override fun setMyCourseNullTagHistoryFailureView() {
        var message = "나의 기록 가져오기 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}