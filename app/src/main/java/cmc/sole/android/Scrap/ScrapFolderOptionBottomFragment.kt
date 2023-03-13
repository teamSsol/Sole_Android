package cmc.sole.android.Scrap

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.Home.MyPage.DialogMyPageLogout
import cmc.sole.android.R
import cmc.sole.android.databinding.BottomFragmentScrapFolderOptionBinding
import cmc.sole.android.databinding.DialogMyPageLogoutBinding
import cmc.sole.android.databinding.FragmentScrapFolderDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScrapFolderOptionBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentScrapFolderOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentScrapFolderOptionBinding.inflate(inflater, container, false)
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
        binding.scrapFolderOptionEdit.setOnClickListener {
            val scrapFolderOptionEditDialog = DialogScrapFolderEdit()
            scrapFolderOptionEditDialog.show(requireActivity().supportFragmentManager, "ScrapFolderEditDialog")
        }

        binding.scrapFolderOptionDelete.setOnClickListener {
            val scrapFolderOptionDeleteDialog = DialogScrapFolderDelete()
            scrapFolderOptionDeleteDialog.show(requireActivity().supportFragmentManager, "ScrapFolderDeleteDialog")
        }
    }
}