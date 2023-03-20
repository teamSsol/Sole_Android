package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryRequest
import cmc.sole.android.MyCourse.Write.MyCourseWriteViewModel
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.TagTranslator
import cmc.sole.android.databinding.BottomFragmentMyCourseTagBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyCourseTagBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentMyCourseTagBinding
    private lateinit var myCourseTagBottomPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomTransRVAdapter: MyCourseTagButtonRVAdapter
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var checkTagList = booleanArrayOf()
    private lateinit var myCourseHistoryRequest: MyCourseHistoryRequest
    private lateinit var dialogFinishListener: OnTagFragmentFinishListener

    interface OnTagFragmentFinishListener {
        fun finish(checkTagList: BooleanArray, myCourseHistoryRequest: MyCourseHistoryRequest)
    }

    fun setOnFinishListener(listener: OnTagFragmentFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()

        for (i in 0..8) {
            checkTagList[i] = myCourseTagBottomPlaceRVAdapter.getItem(i).isChecked
        }
        for (i in 0..4) {
            checkTagList[i + 9] = myCourseTagBottomWithRVAdapter.getItem(i).isChecked
        }
        for (i in 0..3) {
            checkTagList[i + 14] = myCourseTagBottomTransRVAdapter.getItem(i).isChecked
        }

        myCourseHistoryRequest = MyCourseHistoryRequest(returnTag("place"), returnTag("with"), returnTag("trans"))

        dialogFinishListener.finish(checkTagList, myCourseHistoryRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentMyCourseTagBinding.inflate(inflater, container, false)
        checkTagList = requireArguments().getBooleanArray("checkTagList")!!

        initAdapter()
        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.myCourseTagOkBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun initAdapter() {
        myCourseTagBottomPlaceRVAdapter = MyCourseTagButtonRVAdapter(placeTagList)
        binding.myCoursePlaceTagRv.adapter = myCourseTagBottomPlaceRVAdapter
        binding.myCoursePlaceTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomPlaceRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                returnTag("place")
            }
        })

        placeTagList.add(TagButton(1, resources.getString(R.string.place_tag1), checkTagList[0]))
        placeTagList.add(TagButton(2, resources.getString(R.string.place_tag2), checkTagList[1]))
        placeTagList.add(TagButton(3, resources.getString(R.string.place_tag3), checkTagList[2]))
        placeTagList.add(TagButton(4, resources.getString(R.string.place_tag4), checkTagList[3]))
        placeTagList.add(TagButton(5, resources.getString(R.string.place_tag5), checkTagList[4]))
        placeTagList.add(TagButton(6, resources.getString(R.string.place_tag6), checkTagList[5]))
        placeTagList.add(TagButton(7, resources.getString(R.string.place_tag7), checkTagList[6]))
        placeTagList.add(TagButton(8, resources.getString(R.string.place_tag8), checkTagList[7]))
        placeTagList.add(TagButton(9, resources.getString(R.string.place_tag9), checkTagList[8]))
        placeTagList.add(TagButton(null, "", false))

        myCourseTagBottomWithRVAdapter = MyCourseTagButtonRVAdapter(withTagList)
        binding.myCourseWithTagRv.adapter = myCourseTagBottomWithRVAdapter
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomWithRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) { }
        })

        withTagList.add(TagButton(10, resources.getString(R.string.with_tag10), checkTagList[9]))
        withTagList.add(TagButton(11, resources.getString(R.string.with_tag11), checkTagList[10]))
        withTagList.add(TagButton(12, resources.getString(R.string.with_tag12), checkTagList[11]))
        withTagList.add(TagButton(13, resources.getString(R.string.with_tag13), checkTagList[12]))
        withTagList.add(TagButton(14, resources.getString(R.string.with_tag14), checkTagList[13]))
        withTagList.add(TagButton(null, "", false))

        myCourseTagBottomTransRVAdapter = MyCourseTagButtonRVAdapter(transTagList)
        binding.myCourseTransportTagRv.adapter = myCourseTagBottomTransRVAdapter
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomTransRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) { }
        })

        transTagList.add(TagButton(15, resources.getString(R.string.trans_tag15), checkTagList[14]))
        transTagList.add(TagButton(16, resources.getString(R.string.trans_tag16), checkTagList[15]))
        transTagList.add(TagButton(17, resources.getString(R.string.trans_tag17), checkTagList[16]))
        transTagList.add(TagButton(18, resources.getString(R.string.trans_tag18), checkTagList[17]))
        transTagList.add(TagButton(null, "", false))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 85 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun returnTag(option: String): MutableSet<String> {
        var resultTagSet = mutableSetOf<String>()
        when(option) {
            "place" -> {
                for (i in 0..8) {
                    if (myCourseTagBottomPlaceRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(TagTranslator.tagKorToEng(activity as AppCompatActivity, myCourseTagBottomPlaceRVAdapter.getItem(i).title))
                }
            }
            "with" -> {
                for (i in 0..4) {
                    if (myCourseTagBottomWithRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(TagTranslator.tagKorToEng(activity as AppCompatActivity, myCourseTagBottomWithRVAdapter.getItem(i).title))
                }
            }
            else -> {
                for (i in 0..3) {
                    if (myCourseTagBottomTransRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(TagTranslator.tagKorToEng(activity as AppCompatActivity, myCourseTagBottomTransRVAdapter.getItem(i).title))
                }
            }
        }

        return resultTagSet
    }
}