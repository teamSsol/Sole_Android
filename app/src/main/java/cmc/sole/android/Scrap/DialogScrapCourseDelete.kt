package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.Scrap.Retrofit.ScrapCourseDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapDefaultFolderCourseDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.databinding.DialogScrapCourseDeleteBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapCourseDelete: DialogFragment(), ScrapCourseDeleteView, ScrapDefaultFolderCourseDeleteView {

    lateinit var binding: DialogScrapCourseDeleteBinding

    lateinit var scrapService: ScrapService
    private var scrapFolderId: Int = -1
    private var deleteCourseId = ArrayList<Int>()
    private var scrapFolderName = ""
    private lateinit var dialogFinishListener: OnFinishListener

    interface OnFinishListener {
        fun finish()
    }

    fun setOnFinishListener(listener: OnFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogScrapCourseDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        scrapFolderId = requireArguments().getInt("scrapFolderId")
        arguments?.getIntegerArrayList("courseId")?.let { deleteCourseId.addAll(it) }

        scrapFolderName = requireArguments().getString("scrapFolderName").toString()

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapCourseDeleteView(this)
        scrapService.setScrapDefaultFolderDeleteView(this)
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
            if (scrapFolderName == "기본 폴더") {
                var deleteCourse = ""
                for (i in 0 until deleteCourseId.size) {
                    if (i != 0) {
                        deleteCourse += ", "
                    }
                    deleteCourse += deleteCourseId[i]
                }
                scrapService.deleteScrapDefaultFolderCourse(deleteCourse)
            } else {
                scrapService.deleteScrapCourse(scrapFolderId, deleteCourseId)
            }
            dismiss()
        }
    }

    override fun scrapCourseDeleteSuccessView() {
        Toast.makeText(context, "삭제 완료", Toast.LENGTH_LONG).show()
    }

    override fun scrapCourseDeleteFailureView() {
        Toast.makeText(context, "삭제 실패", Toast.LENGTH_LONG).show()
    }

    override fun scrapDefaultFolderCourseDeleteSuccessView() {
        deleteCourseId.clear()
    }

    override fun scrapDefaultFolderCourseDeleteFailureView() {
        deleteCourseId.clear()
    }
}