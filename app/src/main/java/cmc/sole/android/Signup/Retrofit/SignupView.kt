package cmc.sole.android.Signup.Retrofit

interface SignupCheckView {
    fun signupCheckSuccessView(result: SignupCheckResponse)
    fun signupCheckFailureView(responseCode: Int)
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