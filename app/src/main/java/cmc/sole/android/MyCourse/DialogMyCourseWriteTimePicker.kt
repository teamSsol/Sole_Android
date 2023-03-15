package cmc.sole.android.MyCourse

import android.R
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import cmc.sole.android.databinding.DialogTimepickerBinding

class DialogMyCourseWriteTimePicker: DialogFragment() {

    lateinit var binding: DialogTimepickerBinding
    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTimepickerBinding.inflate(inflater, container, false)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
        binding.myCourseWriteTp.setIs24HourView(true)

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
            writeVM.setTime("")
            dismiss()
        }
        
        binding.myCourseWriteTpOkBtn.setOnClickListener {
            var time = binding.myCourseWriteTp.hour.toString() + "시 " + binding.myCourseWriteTp.minute.toString() + "분"
            writeVM.setTime(time)
            dismiss()
        }
    }
}