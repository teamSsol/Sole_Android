package cmc.sole.android.MyCourse.Retrofit

import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.User

data class MyCourseHistoryInfoResponse(
    var data: MyCourseHistoryInfoResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyCourseHistoryInfoResult(
    var mostPlaceCategories: Set<String>,
    var mostRegion: String,
    var mostTransCategories: Set<String>,
    var nickname: String,
    var totalCourses: Int,
    var totalDate: Int,
    var totalPlaces: Int,
    var profileImg: String?
)

data class MyCourseHistoryRequest(
    var placeCategories: Set<Categories>?,
    var transCategories: Set<Categories>?,
    var withCategories: Set<Categories>?
)

data class MyCourseHistoryResponse(
    var data: ArrayList<DefaultCourse>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyCourseAddResponse(
    var data: MyCourseAddResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyCourseAddResult(
    var categories: Set<Categories>,
    var courseId: Int,
    var description: String,
    var distance: Double,
    var duration: Int,
    var placeResponseDtos: ArrayList<MyCourseAddPlace>,
    var scrapCount: Int,
    var startDate: String,
    var thumbnailUrl: String,
    var title: String,
    var writer: User
)

data class MyCourseUpdateResponse(
    var data: ArrayList<DefaultCourse>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyCourseUpdateResult(
    var categories: Set<Categories>,
    var courseId: Int,
    var description: String,
    var distance: Double,
    var duration: Int,
    var placeResponseDtos: ArrayList<MyCourseAddPlace>,
    var scrapCount: Int,
    var startDate: String,
    var thumbnailUrl: String,
    var title: String,
    var writer: User
)

data class UpdatePlaceResponseDtos(
    var address: String,
    var description: String,
    var duration: Int,
    var latitude: Double,
    var longitude: Double,
    var placeId: Int,
    var placeImgUrls: ArrayList<String>,
    var placeName: String
)

data class MyCourseAddPlace(
    var address: String,
    var description: String,
    var placeId: Int,
    var placeImgUrls: ArrayList<String>,
    var placeName: String
)

data class AddPlaceRequestDtos(
    var address: String,
    var description: String,
    var duration: Int,
    var latitude: Double,
    var longitude: Double,
    var placeName: String
)