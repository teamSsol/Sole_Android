package cmc.sole.android.MyCourse.Write

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import cmc.sole.android.MyCourse.Write.MyCourseWriteViewModel
import cmc.sole.android.databinding.DialogTimepickerBinding

class DialogMyCourseWriteTimePicker: DialogFragment() {

    lateinit var binding: DialogTimepickerBinding
    private lateinit var dialogFinishListener: OnDialogFragmentFinishListener
    private var time = ""
    private var hour = ""
    private var minute = ""

    interface OnDialogFragmentFinishListener {
        fun finish(hour: String, minute: String)
    }

    fun setOnFinishListener(listener: OnDialogFragmentFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(hour, minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTimepickerBinding.inflate(inflater, container, false)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
        binding.myCourseWriteTp.setIs24HourView(true)
        binding.myCourseWriteTp.hour = 0
        binding.myCourseWriteTp.minute = 0

        initClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.myCourseWriteTpDeleteCancel.setOnClickListener {
            time = ""
            dismiss()
        }
        
        binding.myCourseWriteTpOkBtn.setOnClickListener {
            time = if (binding.myCourseWriteTp.minute.toString() == "0") {
                if (binding.myCourseWriteTp.hour.toString() == "0") ""
                else binding.myCourseWriteTp.hour.toString() + "시간 "
            } else if (binding.myCourseWriteTp.hour.toString() == "0") {
                binding.myCourseWriteTp.minute.toString() + "분"
            } else {
                binding.myCourseWriteTp.hour.toString() + "시간 " + binding.myCourseWriteTp.minute.toString() + "분"
            }

            hour = binding.myCourseWriteTp.hour.toString()
            minute = binding.myCourseWriteTp.minute.toString()

            dismiss()
        }
    }
}