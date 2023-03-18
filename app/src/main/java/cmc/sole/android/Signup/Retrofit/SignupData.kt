package cmc.sole.android.Signup.Retrofit

import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.User

data class SignupCheckRequest(
    var accessToken: String,
    var fcmToken: String
)

data class SignupCheckResponse(
    var data: User,
    var status: Int,
    var success: Boolean,
    var timestamp: String
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
    var data: User,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)