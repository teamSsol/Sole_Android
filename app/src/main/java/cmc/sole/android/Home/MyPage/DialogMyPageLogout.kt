package cmc.sole.android.Home.MyPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.databinding.DialogMyPageLogoutBinding


class DialogMyPageLogout: DialogFragment() {

    lateinit var binding: DialogMyPageLogoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMyPageLogoutBinding.inflate(inflater, container, false)
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
        binding.logoutCancel.setOnClickListener {
            dismiss()
        }
        
        // UPDATE: 로그아웃 API 연동해주기
        binding.logoutLogout.setOnClickListener {
            Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show()
        }
    }
}