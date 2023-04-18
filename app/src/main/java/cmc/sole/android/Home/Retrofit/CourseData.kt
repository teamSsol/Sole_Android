package cmc.sole.android.Home

import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.User
import com.google.gson.annotations.SerializedName

data class HomeCurrentGPSRequest(
    var latitude: Double,
    var longitude: Double
)

data class HomeCurrentGPSResponse(
    var data: HomeCurrentGPSResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class HomeCurrentGPSResult(
    var currentGps: CurrentGpsInfo,
    var memberId: Int,
    var nickname: String
)

data class CurrentGpsInfo(
    var address: String,
    var distance: Double,
    var latitude: Double,
    var longitude: Double
)
data class HomeCategoriesResponse(
    var data: HomeCategoriesResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class HomeCategoriesResult(
    var memberId: Int,
    var nickname: String,
    var placeCategories: Set<placeCategories?>,
    var transCategories: Set<transCategories?>,
    var withCategories: Set<withCategories?>
)

data class PopularCourse(
    var courseId: Int,
    var courseName: String,
    var thumbnailImg: String
)

data class DefaultCourse(
    var address: String,
    var categories: Set<Categories>,
    var courseId: Int,
    var distance: Double,
    var duration: Int,
    var finalPage: Boolean,
    var like: Boolean,
    var thumbnailImg: String,
    var title: String,
    var viewType: Int?
)

data class HomeDefaultResponse(
    var data: ArrayList<DefaultCourse>,
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

data class HomeCourseDetailResponse(
    var data: HomeCourseDetailResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class HomeCourseDetailResult(
    var categories: Set<Categories>,
    var checkWriter: Boolean,
    var courseId: Int,
    var description: String,
    var distance: Double,
    var duration: Int,
    var followStatus: String,
    var follower: Int,
    var following: Int,
    var placeResponseDtos: ArrayList<PlaceResponseDtos>,
    var scrapCount: Int,
    var startDate: String,
    var title: String,
    @SerializedName("thumbnailUrl") var thumbnailImg: String,
    var writer: User
)

data class PlaceResponseDtos(
    var address: String,
    var description: String,
    var duration: Int,
    var latitude: Double,
    var longitude: Double,
    var placeId: Int,
    var placeImgUrls: ArrayList<MyCourseWriteImage>,
    var placeName: String,
    var viewType: Int? = null
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

const val courseItem = 1
const val courseMoreItem = 2

const val courseDetailNumber = 1
const val courseDetailLine = 2

const val locationImage = 1
const val locationAddImage = 2

const val simpleMode = 1
const val detailMode = 2