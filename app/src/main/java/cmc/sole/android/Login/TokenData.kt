package cmc.sole.android.Login

data class NewTokenResponse(
    var data: NewTokenResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class NewTokenResult(
    var accessToken: String,
    var refreshToken: String
)