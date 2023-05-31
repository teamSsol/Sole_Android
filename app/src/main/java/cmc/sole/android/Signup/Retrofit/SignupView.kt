package cmc.sole.android.Signup.Retrofit

import cmc.sole.android.ErrorResponse

interface SignupCheckView {
    fun signupCheckSuccessView(result: SignupCheckResponse)
    fun signupCheckFailureView(errorResponse: ErrorResponse?)
}

interface SignupNicknameView {
    fun signupNicknameSuccessView(result: Boolean)
    fun signupNicknameFailureView()
}

interface SignupSocialView {
    fun signupSocialSuccessView(result: SignupSocialResponse)
    fun signupSocialFailureView()
}

interface LogoutView {
    fun setLogoutSuccessView()
    fun setLogoutFailureView()
}