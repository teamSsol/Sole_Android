package cmc.sole.android.Follow

data class FollowActivityData(
    val profileImgUrl: String,
    val nickname: String,
    val heart: Boolean,
    val contentImg: String,
    val courseName: String,
    val courseContent: String
)

data class FollowListData(
    val profileImgUrl: String,
    val nickname: String,
    val follower: String,
    val following: String,
    val followFlag: Boolean
)
