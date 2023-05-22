package cmc.sole.android.Home.MyPage

import android.content.Intent
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
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.MyPageMemberQuitView
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.databinding.DialogMyPageWithdrawalBinding

class DialogMyPageWithdrawal: DialogFragment(), MyPageMemberQuitView {

    lateinit var binding: DialogMyPageWithdrawalBinding

    private lateinit var homeService: HomeService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMyPageWithdrawalBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER)

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
        homeService = HomeService()
        homeService.setMyPageMemberQuitView(this)
    }

    private fun initClickListener() {
        binding.withdrawalCancel.setOnClickListener {
            dismiss()
        }

        binding.withdrawalWithdrawal.setOnClickListener {
            // UPDATE: 임시로 주석 처리
            homeService.deleteMember()
        }
    }

    override fun myPageMemberQuitSuccessView() {
        // UPDATE: 테스트 필요
        (context as MyPageInfoSettingActivity).finishAffinity()
        startActivity(Intent(activity, LoginActivity::class.java))
    }

    override fun myPageMemberQuitFailureView() {
        Toast.makeText(activity, "회원 탈퇴 실패", Toast.LENGTH_SHORT)
    }
}