package cmc.sole.android.Signup.Retrofit

interface SignupNicknameView {
    fun signupNicknameSuccessView(result: Boolean)
    fun signupNicknameFailureView()
}

interface SignupSocialView {
    fun signupSocialSuccessView(result: SignupSocialResponse)
    fun signupSocialFailureView()
}