package cmc.sole.android.Home.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.withdrawalCancel.setOnClickListener {
            dismiss()
        }

        // UPDATE: 회원탈퇴 API 연동해주기
        binding.withdrawalWithdrawal.setOnClickListener {
            Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show()
        }
    }
}