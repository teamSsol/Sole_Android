package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import cmc.sole.android.R
import cmc.sole.android.Scrap.Retrofit.*
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapFolderNew: DialogFragment(), ScrapFolderAddView {

    lateinit var binding: DialogScrapFolderNewBinding
    private lateinit var dialogFinishListener: OnFinishListener
    private lateinit var scrapService: ScrapService

    interface OnFinishListener {
        fun finish()
    }

    fun setOnFinishListener(listener: OnFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogScrapFolderNewBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        initService()
        initClickListener()
        initTextListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish()
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderAddView(this)
    }

    private fun initClickListener() {
        binding.scrapFolderNewCancel.setOnClickListener {
            dismiss()
        }
        
        // UPDATE: 폴더 추가
        binding.scrapFolderNewBtn.setOnClickListener {
            scrapService.addScrapFolder(ScrapFolderAddRequest(binding.scrapFolderNewNameEt.text.toString()))
            dismiss()
        }
    }

    private fun initTextListener() {
        binding.scrapFolderNewNameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                if (binding.scrapFolderNewNameEt.length() > 0) {
                    if (binding.scrapFolderNewNameEt.text.toString() != "기본 폴더") {
                        binding.scrapFolderNewBtn.setBackgroundResource(R.drawable.default_button_o)
                        binding.scrapFolderNewBtn.isEnabled = true
                    }
                } else if (binding.scrapFolderNewNameEt.length() == 0) {
                    binding.scrapFolderNewBtn.setBackgroundResource(R.drawable.default_button_x)
                    binding.scrapFolderNewBtn.isEnabled = false
                }
            }
        })
    }

    override fun scrapFolderAddSuccessView(scrapFolderAddResult: ScrapFolderAddResult) {

    }

    override fun scrapFolderAddFailureView() {
        Toast.makeText(context, "폴더 생성 실패", Toast.LENGTH_LONG).show()
    }
}