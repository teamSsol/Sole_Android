package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.BottomFragmentMyCourseTagBinding
import cmc.sole.android.databinding.BottomFragmentScrapFolderOptionBinding
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
    private var checkTagList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentMyCourseTagBinding.inflate(inflater, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        myCourseTagBottomPlaceRVAdapter = MyCourseTagButtonRVAdapter(placeTagList)
        binding.myCoursePlaceTagRv.adapter = myCourseTagBottomPlaceRVAdapter
        binding.myCoursePlaceTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))

        placeTagList.add(TagButton(resources.getString(R.string.place_tag1), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag2), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag3), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag4), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag5), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag6), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag7), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag8), false))
        placeTagList.add(TagButton(resources.getString(R.string.place_tag9), false))
        placeTagList.add(TagButton("", false))

        myCourseTagBottomWithRVAdapter = MyCourseTagButtonRVAdapter(withTagList)
        binding.myCourseWithTagRv.adapter = myCourseTagBottomWithRVAdapter
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))

        withTagList.add(TagButton(resources.getString(R.string.with_tag10), false))
        withTagList.add(TagButton(resources.getString(R.string.with_tag11), false))
        withTagList.add(TagButton(resources.getString(R.string.with_tag12), false))
        withTagList.add(TagButton(resources.getString(R.string.with_tag13), false))
        withTagList.add(TagButton(resources.getString(R.string.with_tag14), false))
        withTagList.add(TagButton("", false))

        myCourseTagBottomTransRVAdapter = MyCourseTagButtonRVAdapter(transTagList)
        binding.myCourseTransportTagRv.adapter = myCourseTagBottomTransRVAdapter
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))

        transTagList.add(TagButton(resources.getString(R.string.trans_tag15), false))
        transTagList.add(TagButton(resources.getString(R.string.trans_tag16), false))
        transTagList.add(TagButton(resources.getString(R.string.trans_tag17), false))
        transTagList.add(TagButton(resources.getString(R.string.trans_tag18), false))
        transTagList.add(TagButton("", false))

        myCourseTagBottomPlaceRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                Log.d("WRITE-TEST", "isChecked2 = ${data.isChecked}")
                checkTagList.add(data.title)
            }
        })
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
}