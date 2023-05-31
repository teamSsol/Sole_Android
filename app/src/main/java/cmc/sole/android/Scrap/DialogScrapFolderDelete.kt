package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.Scrap.Retrofit.ScrapDefaultFolderCourseDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.databinding.DialogScrapFolderDeleteBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapFolderDelete: DialogFragment(), ScrapFolderDeleteView {

    lateinit var binding: DialogScrapFolderDeleteBinding

    lateinit var scrapService: ScrapService
    private var scrapFolderId: Int = -1
    private lateinit var dialogFinishListener: OnFinishListener
    private var mode = ""
    private var deleteCourseId = ArrayList<Int>()
    private var folderName = ""

    interface OnFinishListener {
        fun finish(mode: String)
    }

    fun setOnFinishListener(listener: OnFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(mode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogScrapFolderDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        scrapFolderId = requireArguments().getInt("scrapFolderId")
        deleteCourseId = requireArguments().getIntegerArrayList("deleteCourseId") as ArrayList<Int>
        folderName = requireArguments().getString("folderName").toString()

        binding.scrapFolderDeleteTitleTv.text = "'${folderName}'\n폴더를 삭제하시겠어요?"

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderDeleteView(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.scrapFolderDeleteCancel.setOnClickListener {
            dismiss()
        }
        
        binding.scrapFolderDeleteBtn.setOnClickListener {
            scrapService.deleteScrapFolder(scrapFolderId)
            mode = "delete"
        }
    }

    override fun scrapFolderDeleteSuccessView() {
        dismiss()
    }

    override fun scrapFolderDeleteFailureView() {
        Toast.makeText(context, "폴더 삭제 실패", Toast.LENGTH_LONG).show()
    }
}