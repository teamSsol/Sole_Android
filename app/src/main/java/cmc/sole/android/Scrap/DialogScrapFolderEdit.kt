package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.Scrap.Retrofit.ScrapFolderNameUpdateRequest
import cmc.sole.android.Scrap.Retrofit.ScrapFolderNameUpdateView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.databinding.DialogScrapFolderEditBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapFolderEdit: DialogFragment(),
    ScrapFolderNameUpdateView {

    lateinit var binding: DialogScrapFolderEditBinding
    private lateinit var dialogFinishListener: OnFinishListener
    private lateinit var scrapService: ScrapService
    private var scrapFolderId = -1
    private var mode = ""
    private var folderEditName = ""

    interface OnFinishListener {
        fun finish(dialogMode: String, data: String)
    }

    fun setOnFinishListener(listener: OnFinishListener) {
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
        binding = DialogScrapFolderEditBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        scrapFolderId = requireArguments().getInt("scrapFolderId")

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderNameUpdateView(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.scrapFolderEditCancel.setOnClickListener {
            dismiss()
        }

        // UPDATE: 폴더명 수정 API 연동
        binding.scrapFolderEditBtn.setOnClickListener {
            folderEditName = binding.scrapFolderEditNameEt.text.toString()
            scrapService.updateScrapFolderName(scrapFolderId, ScrapFolderNameUpdateRequest(folderEditName))
        }
    }

    override fun scrapFolderNameUpdateSuccessView() {
        mode = "edit"
        dismiss()
    }

    override fun scrapFolderNameUpdateFailureView() {
        Toast.makeText(context, "폴더명 수정 실패", Toast.LENGTH_LONG).show()
    }
}