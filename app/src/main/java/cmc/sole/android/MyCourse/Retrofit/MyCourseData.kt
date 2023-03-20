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

data class MyCourseAddResponse(
    var data: MyCourseAddResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

/*
{
  "date": "2023-03-02",
  "description": "제주도로 떠나요~~",
  "distance": 0,
  "placeCategories": [
    "ACTIVITY"
  ],
  "placeRequestDtos": [
    {
      "address": "서울시 강남구 논현동 217-41",
      "description": "중식집",
      "duration": 80,
      "latitude": 45.43,
      "longitude": 23.22,
      "placeName": "짜장면집"
    }
  ],
  "title": "제주도 여행...!",
  "transCategories": [
    "BIKE"
  ],
  "withCategories": [
    "ALONE"
  ]
}
 */

data class MyCourseAddResult(
    var categories: Set<Categories>,
    var courseId: Int,
    var description: String,
    var distance: Int,
    var duration: Int,
    var placeResponseDtos: ArrayList<MyCourseAddPlace>,
    var scrapCount: Int,
    var startDate: String,
    var thumbnailUrl: String,
    var title: String,
    var writer: User
)

data class MyCourseAddPlace(
    var address: String,
    var description: String,
    var placeId: Int,
    var placeImgUrls: ArrayList<String>,
    var placeName: String
)

data class PlaceRequestDtos(
    var address: String,
    var description: String,
    var duration: Int,
    var latitude: Double,
    var longitude: Double,
    var placeName: String
)