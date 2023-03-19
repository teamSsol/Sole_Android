package cmc.sole.android.MyCourse.Retrofit

data class MyCourseHistoryResponse(
    var data: MyCourseHistoryResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class MyCourseHistoryResult(
    var mostPlaceCategories: Set<String>,
    var mostRegion: String,
    var mostTransCategories: Set<String>,
    var nickname: String,
    var totalCourses: Int,
    var totalDate: Int,
    var totalPlaces: Int
)