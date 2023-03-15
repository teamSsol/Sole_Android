package cmc.sole.android.MyCourse

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import cmc.sole.android.databinding.FragmentDatepickerBinding
import java.text.SimpleDateFormat
import java.util.*

class DialogMyCourseWriteDatePicker: DialogFragment() {

    lateinit var binding: FragmentDatepickerBinding
    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatepickerBinding.inflate(inflater, container, false)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
        binding.myCourseWriteDp.maxDate = System.currentTimeMillis()

        initClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.myCourseWriteDpDeleteCancel.setOnClickListener {
            writeVM.setDate("")
            dismiss()
        }
        
        binding.myCourseWriteDpOkBtn.setOnClickListener {
            writeVM.setDate(setDateValue())
            dismiss()
        }
    }

    private fun setDateValue(): String {
        var calendar = Calendar.getInstance()
        calendar.set(
            binding.myCourseWriteDp.year,
            binding.myCourseWriteDp.month,
            binding.myCourseWriteDp.dayOfMonth
        )

        return SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(Date(calendar.timeInMillis))
    }
}