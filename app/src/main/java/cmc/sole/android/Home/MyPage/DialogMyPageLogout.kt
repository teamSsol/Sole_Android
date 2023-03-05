package cmc.sole.android.Home.MyPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        initClickListener()

        return binding.root
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