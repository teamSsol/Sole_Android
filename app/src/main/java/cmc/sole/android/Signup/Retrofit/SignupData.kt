package cmc.sole.android.Signup.Retrofit

import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories

data class SignupCheckRequest(
    var accessToken: String,
    var fcmToken: String
)

data class SignupCheckResponse(
    var data: SignupCheckResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class SignupCheckResult(
    var accessToken: String,
    var check: Boolean,
    var memberId: Int,
    var nickname: String,
    var profileImgUrl: String?,
    var refreshToken: String,
    var role: String,
    var social: String = "KAKAO",
    var socialId: String
)

data class SignupNicknameRequest(
    var nickname: String
)

data class SignupSocialRequest(
    var accessToken: String,
    var fcmToken: String,
    var infoAccepted: Boolean,
    var marketingAccepted: Boolean,
    var nickname: String,
    var placeCategories: Enum<placeCategories>,
    var serviceAccepted: Boolean,
    var transCategories: Enum<transCategories>,
    var withCategories: Enum<withCategories>
)

data class SignupSocialResponse(
    var data: SignupSocialResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class SignupSocialResult(
    var accessToken: String,
    var check: Boolean,
    var memberId: Int,
    var nickname: String,
    var profileImgUrl: String?,
    var refreshToken: String?,
    var social: String,
    var socialId: String
)