package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.Scrap.Retrofit.ScrapCourseDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.databinding.DialogScrapCourseDeleteBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapCourseDelete: DialogFragment(), ScrapCourseDeleteView {

    lateinit var binding: DialogScrapCourseDeleteBinding

    lateinit var scrapService: ScrapService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogScrapCourseDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapCourseDeleteView(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.scrapCourseDeleteCancel.setOnClickListener {
            dismiss()
        }
        
        binding.scrapCourseDeleteBtn.setOnClickListener {
            Toast.makeText(context, "삭제하기", Toast.LENGTH_SHORT).show()
            // scrapService.deleteScrapCourse()
        }
    }

    override fun scrapCourseDeleteSuccessView() {
        TODO("Not yet implemented")
    }

    override fun scrapCourseDeleteFailureView() {
        TODO("Not yet implemented")
    }
}