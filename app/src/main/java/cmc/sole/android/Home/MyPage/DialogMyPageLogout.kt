package cmc.sole.android.Home.MyPage

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.Signup.Retrofit.LogoutView
import cmc.sole.android.Signup.Retrofit.SignupService
import cmc.sole.android.databinding.DialogMyPageLogoutBinding


class DialogMyPageLogout: DialogFragment(),
    LogoutView {

    lateinit var binding: DialogMyPageLogoutBinding
    private lateinit var signupService: SignupService
    private lateinit var dialogFinishListener: OnFinishListener
    var result = false

    interface OnFinishListener {
        fun finish(result: Boolean)
    }

    fun setOnFinishListener(listener: OnFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(result)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMyPageLogoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        signupService = SignupService()
        signupService.setLogoutView(this)
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

        binding.logoutLogout.setOnClickListener {
            signupService.logout()
        }
    }

    override fun setLogoutSuccessView() {
        result = true
        dismiss()
    }

    override fun setLogoutFailureView() {
        result = false
        Toast.makeText(context, "로그아웃 실패", Toast.LENGTH_SHORT).show()
    }
}