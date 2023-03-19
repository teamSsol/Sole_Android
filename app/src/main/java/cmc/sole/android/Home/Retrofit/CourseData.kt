package cmc.sole.android.Home

import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.User

data class PopularCourse(
    var courseId: Int,
    var courseName: String,
    var thumbnailImg: String
)

data class DefaultCourse(
    var address: String,
    var categories: Set<Categories>,
    var courseId: Int,
    var distance: Int,
    var duration: Int,
    var like: Boolean,
    var thumbnailImg: String,
    var title: String,
    var viewType: Int? = null
)

data class HomeDefaultResponse(
    var data: ArrayList<DefaultCourse>?,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class HomePopularResponse(
    var data: ArrayList<PopularCourse>?,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyCourseWriteImage(
    var imgUrl: String,
    var viewType: Int?
)

// MEMO: 마이페이지
data class MyPageInfoResponse(
    var data: MyPageInfoResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyPageInfoResult(
    var description: String,
    var follower: Int,
    var following: Int,
    var nickname: String,
    var profileImgUrl: String,
    var social: String,
    var socialId: String
)

data class MyPageUpdateRequest(
    var description: String,
    var nickname: String
)

data class MyPageUpdateResponse(
    var data: MyPageInfoResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

// MEMO: 마이페이지 알림
data class MyPageNotificationResponse(
    var data: MyPageNotificationResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyPageNotificationResult(
    var activityNot: Boolean,
    var marketingNot: Boolean,
    var nickname: String
)

data class MyPageNotificationUpdateRequest(
    var activityNot: Boolean,
    var marketingNot: Boolean
)

data class MyPageNotificationUpdateResponse(
    var data: MyPageNotificationResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyPageNotificationHistoryResponse(
    var data: ArrayList<MyPageNotificationHistoryResult?>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyPageNotificationHistoryResult(
    var content: String,
    var createdAt: String,
    var title: String,
    var type: String
)

// MEMO: 마이페이지 공지사항
data class MyPageNoticeResponse(
    var data: ArrayList<MyPageNoticeResult>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyPageNoticeResult(
    var content: String,
    var createdAt: String,
    var noticeId: Int,
    var title: String,
    var writer: User
)

data class MyPageNoticeAddRequest(
    var content: String,
    var title: String
)

data class MyPageNoticeAddResponse(
    var data: MyPageNoticeResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

const val courseDetailNumber = 1
const val courseDetailLine = 2

const val locationImage = 1
const val locationAddImage = 2