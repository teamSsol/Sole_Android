package cmc.sole.android.Login

interface NewTokenView {
    fun getNewTokenSuccessView(result: NewTokenResult)
    fun getNewTokenFailureView()
}