package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteOptionBinding
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteOptionNewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyCourseWriteOptionBottomFragment: BottomSheetDialogFragment() {

    // lateinit var binding: BottomFragmentMyCourseWriteOptionBinding
    lateinit var binding: BottomFragmentMyCourseWriteOptionNewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding = BottomFragmentMyCourseWriteOptionBinding.inflate(inflater, container, false)
        binding = BottomFragmentMyCourseWriteOptionNewBinding.inflate(inflater, container, false)

        // (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.my_course_write_option_fl, MyCourseWriteLocationBottomFragment()).commit()
        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.myCourseWriteOptionLocationTv.setOnClickListener {
            // activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.my_course_write_option_fl, MyCourseWriteLocationBottomFragment())?.commit()
            binding.myCourseWriteOptionTagLayout.visibility = View.GONE
            binding.myCourseWriteOptionLocationLayout.visibility = View.VISIBLE
        }

        binding.myCourseWriteOptionTasteTv.setOnClickListener {
            // activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.my_course_write_option_fl, MyCourseWriteTagBottomFragment())?.commit()
            binding.myCourseWriteOptionTagLayout.visibility = View.VISIBLE
            binding.myCourseWriteOptionLocationLayout.visibility = View.GONE
        }
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
        return getWindowHeight() * 80 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}