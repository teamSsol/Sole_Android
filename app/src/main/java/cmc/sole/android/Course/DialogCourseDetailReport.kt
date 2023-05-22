package cmc.sole.android.Course

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.MyCourse.Retrofit.MyCourseReportView
import cmc.sole.android.MyCourse.Retrofit.MyCourseService
import cmc.sole.android.databinding.DialogCourseDetailDeleteBinding
import cmc.sole.android.databinding.DialogCourseDetailReportBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogCourseDetailReport: DialogFragment(), MyCourseReportView {

    lateinit var binding: DialogCourseDetailReportBinding
    lateinit var myCourseService: MyCourseService
    var courseId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCourseDetailReportBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        courseId = requireArguments().getInt("courseId", -1)

        initService()
        initClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initService() {
        myCourseService = MyCourseService()
        myCourseService.setMyCourseReportView(this)
    }

    private fun initClickListener() {
        binding.courseDetailReportCancel.setOnClickListener {
            dismiss()
        }
        
        binding.courseDetailReportBtn.setOnClickListener {
            myCourseService.reportCourse(courseId)
            dismiss()
        }
    }

    override fun setMyCourseReportSuccessView() {
        Toast.makeText(activity, "코스를 정상적으로 신고했습니다", Toast.LENGTH_SHORT).show()
    }

    override fun setMyCourseReportFailureView() {
        Toast.makeText(activity, "코스 신고하기를 실패했습니다", Toast.LENGTH_SHORT).show()
    }
}