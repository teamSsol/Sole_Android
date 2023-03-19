package cmc.sole.android.Follow

import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.User

data class FollowCourseResponse(
    var data: ArrayList<FollowCourseResult>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class FollowCourseResult(
    var courseId: Int,
    var description: String,
    var like: Boolean,
    var nickname: String,
    var profileImg: String,
    var thumbnailImg: String,
    var title: String
)

data class FollowListResponse(
    var data: ArrayList<FollowUserDataResult>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class FollowUserDataResult(
    var followId: Int,
    var followStatus: String,
    var followerCount: Int,
    var followingCount: Int,
    var member: User
)

data class FollowUserInfoResponse(
    var data: FollowUserInfoResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class FollowUserInfoResult(
    var description: String,
    var followStatus: String,
    var followerCount: Int,
    var followingCount: Int,
    var nickname: String,
    var popularCourse: DefaultCourse,
    var profileImg: String,
    var recentCourses: ArrayList<DefaultCourse>
)