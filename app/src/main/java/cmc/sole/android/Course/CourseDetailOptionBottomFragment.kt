package cmc.sole.android.Course

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.MyCourse.Write.MyCourseWriteActivity
import cmc.sole.android.databinding.BottomFragmentCourseDetailOptionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CourseDetailOptionBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentCourseDetailOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentCourseDetailOptionBinding.inflate(inflater, container, false)
        initClickListener()
        return binding.root
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
        return getWindowHeight() * 20 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun initClickListener() {
        binding.courseDetailOptionEdit.setOnClickListener {
            startActivity(Intent(activity, MyCourseWriteActivity::class.java))
        }

        binding.courseDetailOptionDelete.setOnClickListener {
            val courseDetailDeleteDialog = DialogCourseDetailDelete()
            courseDetailDeleteDialog.show(requireActivity().supportFragmentManager, "CourseDetailDeleteDialog")
        }
    }
}