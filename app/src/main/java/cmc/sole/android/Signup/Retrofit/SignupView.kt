package cmc.sole.android.Signup.Retrofit

interface SignupCheckView {
    fun signupCheckSuccessView(result: SignupCheckResponse)
    fun signupCheckFailureView()
}

interface SignupNicknameView {
    fun signupNicknameSuccessView(result: Boolean)
    fun signupNicknameFailureView()
}

interface SignupSocialView {
    fun signupSocialSuccessView(result: SignupSocialResponse)
    fun signupSocialFailureView()
}