package cmc.sole.android.MyCourse

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.MyCourse.Retrofit.*
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.NewDynamicDrawableSpan
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.TagTranslator
import cmc.sole.android.databinding.FragmentMyCourseBinding


class MyCourseFragment: BaseFragment<FragmentMyCourseBinding>(FragmentMyCourseBinding::inflate),
    MyCourseHistoryInfoView, MyCourseHistoryView {

    lateinit var myCourseCourseRVAdapter: MyCourseCourseRVAdapter
    var myCourseCourseList = ArrayList<DefaultCourse>()
    private var checkTagList = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    lateinit var myCourseService: MyCourseService
    var courseId = 5

    override fun initAfterBinding() {
        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        myCourseService = MyCourseService()
        myCourseService.setMyCourseHistoryInfoView(this)
        myCourseService.setMyCourseHistoryView(this)
        myCourseService.getMyCourseHistoryInfo()
        myCourseService.getMyCourseHistory(courseId, MyCourseHistoryRequest(setOf(), setOf(), setOf()))
    }

    private fun initAdapter() {
        myCourseCourseRVAdapter = MyCourseCourseRVAdapter(myCourseCourseList)
        binding.myCourseCourseRv.adapter = myCourseCourseRVAdapter
        binding.myCourseCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.myCourseCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 20))
    }

    private fun initClickListener() {
        binding.myCourseLocationTag.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            var bundle = Bundle()
            bundle.putBooleanArray("checkTagList", checkTagList)
            myCourseTagBottomFragment.arguments = bundle
            myCourseTagBottomFragment.show(requireActivity().supportFragmentManager, "myCourseTagBottom")
            myCourseTagBottomFragment.setOnFinishListener(object: MyCourseTagBottomFragment.OnTagFragmentFinishListener {
                override fun finish(checkTagList: BooleanArray, myCourseHistoryRequest: MyCourseHistoryRequest) {
                    this@MyCourseFragment.checkTagList = checkTagList
                    checkOptionTag()
                    myCourseService.getMyCourseHistory(courseId, myCourseHistoryRequest)
                }
            })
        }

        binding.myCourseWithTag.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            var bundle = Bundle()
            bundle.putBooleanArray("checkTagList", checkTagList)
            myCourseTagBottomFragment.arguments = bundle
            myCourseTagBottomFragment.show(requireActivity().supportFragmentManager, "myCourseTagBottom")
            myCourseTagBottomFragment.setOnFinishListener(object: MyCourseTagBottomFragment.OnTagFragmentFinishListener {
                override fun finish(checkTagList: BooleanArray, myCourseHistoryRequest: MyCourseHistoryRequest) {
                    this@MyCourseFragment.checkTagList = checkTagList
                    checkOptionTag()
                    myCourseService.getMyCourseHistory(courseId, myCourseHistoryRequest)
                }
            })
        }

        binding.myCourseTransportTag.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            var bundle = Bundle()
            bundle.putBooleanArray("checkTagList", checkTagList)
            myCourseTagBottomFragment.arguments = bundle
            myCourseTagBottomFragment.show(requireActivity().supportFragmentManager, "myCourseTagBottom")
            myCourseTagBottomFragment.setOnFinishListener(object: MyCourseTagBottomFragment.OnTagFragmentFinishListener {
                override fun finish(checkTagList: BooleanArray, myCourseHistoryRequest: MyCourseHistoryRequest) {
                    this@MyCourseFragment.checkTagList = checkTagList
                    checkOptionTag()
                    myCourseService.getMyCourseHistory(courseId, myCourseHistoryRequest)
                }
            })
        }

        binding.myCourseFb.setOnClickListener {
            startActivity(Intent(activity, MyCourseWriteActivity::class.java))
        }
    }

    override fun setMyCourseHistoryInfoSuccessView(myCourseHistoryResult: MyCourseHistoryInfoResult) {
        // UPDATE: API 프로필 이미지 추가하면 추가해주기!
        // Glide.with(this).load(myCourseHistoryResult.profileImg)
        binding.myCourseNicknameTv.text = myCourseHistoryResult.nickname

        // UPDATE: Text Span 처리 필요
        var totalDate = myCourseHistoryResult.totalDate
        var totalPlaces = myCourseHistoryResult.totalPlaces
        var totalCourses = myCourseHistoryResult.totalCourses
        var infoContent = "지금까지 $totalDate 일간 $totalPlaces 곳의 장소를 방문하며,\n이번 달 총 $totalCourses 개의 코스를 기록했어요"
        binding.myCourseInfoContent.text = setSpannableText(infoContent, "top")

        var mostRegion = TagTranslator.tagEngToKor(activity as AppCompatActivity, myCourseHistoryResult.mostRegion)
        var mostTransCategories = TagTranslator.tagEngToKor(activity as AppCompatActivity, myCourseHistoryResult.mostTransCategories.elementAt(0))
        var mostPlaceCategories = TagTranslator.tagEngToKor(activity as AppCompatActivity, myCourseHistoryResult.mostPlaceCategories.elementAt(0))

        if (mostRegion == "." || mostTransCategories == "." || mostPlaceCategories == ".") {
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
        showToast("나의 기록 정보 가져오기 실패")
    }

    override fun setMyCourseHistorySuccessView(myCourseHistoryResult: ArrayList<DefaultCourse>) {
        myCourseCourseRVAdapter.addAllItems(myCourseHistoryResult)
    }

    override fun setMyCourseHistoryFailureView() {
        showToast("나의 기록 가져오기 실패")
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
                builder.setSpan(NewDynamicDrawableSpan(requireContext(), Color.parseColor("#EDEDED"), Color.parseColor("#000000")), dateTextLength[0], dateTextLength[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                return builder
            }
            else -> {
                // MEMO: TextSpan 적용 -> [?, ?, 으로, 이동해서, ?, ?, 를, 다녔어요]
                var placeTextLength = returnTextLength(textArray, 1, option)
                var courseTextLength = returnTextLength(textArray, 5, option)

                builder.setSpan(NewDynamicDrawableSpan(requireContext(), Color.parseColor("#EDEDED"), Color.parseColor("#000000")), placeTextLength[0], placeTextLength[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(NewDynamicDrawableSpan(requireContext(), Color.parseColor("#EDEDED"), Color.parseColor("#000000")), courseTextLength[0], courseTextLength[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

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

    private fun checkOptionTag() {
        for (i in 0..8) {
            if (checkTagList[i]) {
                binding.myCourseLocationTag.strokeColor = ContextCompat.getColor(requireContext(), R.color.main)
                break
            } else if (i == 8) {
                binding.myCourseLocationTag.strokeColor = Color.parseColor("#D3D4D5")
            }
        }

        for (i in 9..13) {
            if (checkTagList[i]) {
                binding.myCourseWithTag.strokeColor = ContextCompat.getColor(requireContext(), R.color.main)
                break
            } else if (i == 13) {
                binding.myCourseWithTag.strokeColor = Color.parseColor("#D3D4D5")
            }
        }

        for (i in 14..17) {
            if (checkTagList[i]) {
                binding.myCourseTransportTag.strokeColor = ContextCompat.getColor(requireContext(), R.color.main)
                break
            } else if (i == 17) {
                binding.myCourseTransportTag.strokeColor = Color.parseColor("#D3D4D5")
            }
        }
    }
}