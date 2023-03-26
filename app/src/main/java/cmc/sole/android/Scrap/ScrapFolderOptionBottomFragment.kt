package cmc.sole.android.Scrap

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.databinding.BottomFragmentScrapFolderOptionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScrapFolderOptionBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentScrapFolderOptionBinding
    private lateinit var dialogFinishListener: OnScrapOptionFinishListener
    var folderEditName: String = ""
    var scrapFolderId: Int = -1
    private var deleteCourseId = ArrayList<Int>()

    private var mode = ""

    interface OnScrapOptionFinishListener {
        fun finish(mode: String, newFolderName: String?)
    }

    fun setOnFinishListener(listener: OnScrapOptionFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(mode, folderEditName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentScrapFolderOptionBinding.inflate(inflater, container, false)
        scrapFolderId = requireArguments().getInt("scrapFolderId")
        deleteCourseId = requireArguments().getIntegerArrayList("deleteCourseId") as ArrayList<Int>

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
        return getWindowHeight() * 15 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun initClickListener() {
        binding.scrapFolderOptionEdit.setOnClickListener {
            val scrapFolderOptionEditDialog = DialogScrapFolderEdit()
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            scrapFolderOptionEditDialog.arguments = bundle
            scrapFolderOptionEditDialog.show(requireActivity().supportFragmentManager, "ScrapFolderEditDialog")
            scrapFolderOptionEditDialog.setOnFinishListener(object: DialogScrapFolderEdit.OnFinishListener {
                override fun finish(dialogMode: String, data: String) {
                    if (dialogMode == "edit") {
                        mode = "edit"
                        folderEditName = data
                        dismiss()
                    }
                }
            })
        }

        binding.scrapFolderOptionDelete.setOnClickListener {
            // dialogFinishListener.finish(folderEditName)
            val scrapFolderOptionDeleteDialog = DialogScrapFolderDelete()
            var bundle = Bundle()
            bundle.putInt("scrapFolderId", scrapFolderId)
            bundle.putIntegerArrayList("deleteCourseId", deleteCourseId)
            scrapFolderOptionDeleteDialog.arguments = bundle
            scrapFolderOptionDeleteDialog.show(requireActivity().supportFragmentManager, "ScrapFolderDeleteDialog")
            scrapFolderOptionDeleteDialog.setOnFinishListener(object: DialogScrapFolderDelete.OnFinishListener {
                override fun finish(dialogMode: String) {
                    if (dialogMode == "delete") {
                        mode = "delete"
                        dismiss()
                    }
                }
            })
        }
    }
}