package cmc.sole.android.Home.MyPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.databinding.DialogMyPageWithdrawalBinding

class DialogMyPageWithdrawal: DialogFragment() {

    lateinit var binding: DialogMyPageWithdrawalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMyPageWithdrawalBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER)

        initClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.withdrawalCancel.setOnClickListener {
            dismiss()
        }

        // UPDATE: 회원탈퇴 API 연동해주기
        binding.withdrawalWithdrawal.setOnClickListener {
            Toast.makeText(context, "탈퇴하기", Toast.LENGTH_SHORT).show()
        }
    }
}