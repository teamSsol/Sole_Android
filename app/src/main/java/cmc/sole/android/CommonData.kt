package cmc.sole.android

data class DefaultResponse(
    var data: String,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class User(
    var accessToken: String,
    var check: Boolean,
    var memberId: Int,
    var nickname: String,
    var profileImgUrl: String?,
    var refreshToken: String,
    var role: String,
    var social: String,
    var socialId: String
)
