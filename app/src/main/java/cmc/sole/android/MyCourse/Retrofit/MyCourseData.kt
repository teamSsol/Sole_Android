package cmc.sole.android.MyCourse.Retrofit

import cmc.sole.android.Home.DefaultCourse

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
    var totalPlaces: Int
)

data class MyCourseHistoryRequest(
    var placeCategories: Set<String>,
    var transCategories: Set<String>,
    var withCategories: Set<String>
)

data class MyCourseHistoryResponse(
    var data: ArrayList<DefaultCourse>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)