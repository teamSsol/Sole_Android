package cmc.sole.android.Course

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.Retrofit.MyCourseDeleteView
import cmc.sole.android.MyCourse.Retrofit.MyCourseService
import cmc.sole.android.databinding.DialogCourseDetailDeleteBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogCourseDetailDelete: DialogFragment(), MyCourseDeleteView {

    lateinit var binding: DialogCourseDetailDeleteBinding
    private lateinit var myCourseService: MyCourseService
    private var courseId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCourseDetailDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        courseId = requireArguments().getInt("courseId", -1)

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        myCourseService = MyCourseService()
        myCourseService.setMyCourseDeleteView(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.courseDetailDeleteCancel.setOnClickListener {
            dismiss()
        }
        
        binding.courseDetailDeleteBtn.setOnClickListener {
            myCourseService.deleteCourse(courseId)
        }
    }

    override fun setMyCourseDeleteSuccessView() {
        Toast.makeText(context, "코스 삭제 완료", Toast.LENGTH_SHORT).show()
    }

    override fun setMyCourseDeleteFailureView() {
        Toast.makeText(context, "코스 삭제 실패", Toast.LENGTH_SHORT).show()
    }
}