package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import cmc.sole.android.databinding.DialogScrapFolderEditBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapFolderEdit: DialogFragment() {

    lateinit var binding: DialogScrapFolderEditBinding
    private lateinit var dialogFinishListener: OnFinishListener

    interface OnFinishListener {
        fun finish(data: String)
    }

    fun setOnFinishListener(listener: OnFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogScrapFolderEditBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        initClickListener()

        return binding.root
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
        
        // UPDATE: 폴더 추가
        binding.scrapFolderEditBtn.setOnClickListener {
            dialogFinishListener.finish(binding.scrapFolderEditNameEt.text.toString())
            dismiss()
        }
    }
}