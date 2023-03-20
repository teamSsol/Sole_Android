package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cmc.sole.android.MyCourse.Write.MyCourseWriteViewModel
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.BottomFragmentMyCourseTagBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyCourseWriteTagBottomFragmentt: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentMyCourseTagBinding
    private lateinit var myCourseTagBottomPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomTransRVAdapter: MyCourseTagButtonRVAdapter
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var checkTagList: MutableList<TagButton> = mutableListOf()
    private lateinit var dialogFinishListener: OnTagFragmentFinishListener

    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    interface OnTagFragmentFinishListener {
        fun finish()
    }

    fun setOnFinishListener(listener: OnTagFragmentFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FIX: 태그 여러 개로 다시 나오는 오류 수정 필요!!
        binding = BottomFragmentMyCourseTagBinding.inflate(inflater, container, false)
        Log.d("WRITE-TEST", "1 ${writeVM.getTag()}")
        // checkTagList = writeVM.getTag()
        initAdapter()
        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.myCourseTagOkBtn.setOnClickListener {
            var tagResult: ArrayList<TagButton> = arrayListOf()

            if (writeVM.getTag() != null) {
                var tagCheckList = writeVM.getTag()!!
                for (i in 0 until writeVM.getTag()!!.size - 1) {
                    tagResult.add(tagCheckList[i])
                }
            }

            // tagResult.add(writeVM.getTag())

            for (element in checkTagList) {
                tagResult.add(element)
            }
            tagResult.add(TagButton(1000, "", false))

            writeVM.setTag(tagSort(tagResult))
            Log.d("WRITE-TEST", "tagResult = $tagResult")
            Log.d("WRITE-TEST", "\n-------------------------------")
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
                Log.d("WRITE-TEST", "checkTagLiST 1 = $checkTagList")
                if (data.isChecked) {
                    checkTagList.add(data)
                }
                else {
                    for (i in 0 until checkTagList.size) {
                        if (data.title == checkTagList[i].title) {
                            checkTagList.removeAt(i)
                            break
                        }
                    }
                }
                writeVM.setCheckTag(position)
                Log.d("WRITE-TEST", "checkTagLiST 2 = $checkTagList")
                Log.d("WRITE-TEST", "writeVM = ${writeVM.getTag()}")
            }
        })

        placeTagList.add(TagButton(1, resources.getString(R.string.place_tag1), writeVM.getCheckTag(0)))
        placeTagList.add(TagButton(2, resources.getString(R.string.place_tag2), writeVM.getCheckTag(1)))
        placeTagList.add(TagButton(3, resources.getString(R.string.place_tag3), writeVM.getCheckTag(2)))
        placeTagList.add(TagButton(4, resources.getString(R.string.place_tag4), writeVM.getCheckTag(3)))
        placeTagList.add(TagButton(5, resources.getString(R.string.place_tag5), writeVM.getCheckTag(4)))
        placeTagList.add(TagButton(6, resources.getString(R.string.place_tag6), writeVM.getCheckTag(5)))
        placeTagList.add(TagButton(7, resources.getString(R.string.place_tag7), writeVM.getCheckTag(6)))
        placeTagList.add(TagButton(8, resources.getString(R.string.place_tag8), writeVM.getCheckTag(7)))
        placeTagList.add(TagButton(9, resources.getString(R.string.place_tag9), writeVM.getCheckTag(8)))
        placeTagList.add(TagButton(null, "", false))

        myCourseTagBottomWithRVAdapter = MyCourseTagButtonRVAdapter(withTagList)
        binding.myCourseWithTagRv.adapter = myCourseTagBottomWithRVAdapter
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomWithRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                if (data.isChecked) {
                    checkTagList.add(data)
                }
                else {
                    for (i in 0 until checkTagList.size) {
                        if (data.title == checkTagList[i].title) {
                            checkTagList.removeAt(i)
                            break
                        }
                    }
                }
                writeVM.setCheckTag(position + 9)
            }
        })

        withTagList.add(TagButton(10, resources.getString(R.string.with_tag10), writeVM.getCheckTag(9)))
        withTagList.add(TagButton(11, resources.getString(R.string.with_tag11), writeVM.getCheckTag(10)))
        withTagList.add(TagButton(12, resources.getString(R.string.with_tag12), writeVM.getCheckTag(11)))
        withTagList.add(TagButton(13, resources.getString(R.string.with_tag13), writeVM.getCheckTag(12)))
        withTagList.add(TagButton(14, resources.getString(R.string.with_tag14), writeVM.getCheckTag(13)))
        withTagList.add(TagButton(null, "", false))

        myCourseTagBottomTransRVAdapter = MyCourseTagButtonRVAdapter(transTagList)
        binding.myCourseTransportTagRv.adapter = myCourseTagBottomTransRVAdapter
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomTransRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                if (data.isChecked) {
                    checkTagList.add(data)
                }
                else {
                    for (i in 0 until checkTagList.size) {
                        if (data.title == checkTagList[i].title) {
                            checkTagList.removeAt(i)
                            break
                        }
                    }
                }
                writeVM.setCheckTag(position + 14)
            }
        })

        transTagList.add(TagButton(15, resources.getString(R.string.trans_tag15), writeVM.getCheckTag(14)))
        transTagList.add(TagButton(16, resources.getString(R.string.trans_tag16), writeVM.getCheckTag(15)))
        transTagList.add(TagButton(17, resources.getString(R.string.trans_tag17), writeVM.getCheckTag(16)))
        transTagList.add(TagButton(18, resources.getString(R.string.trans_tag18), writeVM.getCheckTag(17)))
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

    private fun tagSort(list: List<TagButton>): List<TagButton> {
        return list.sortedBy { it.index }
    }
}