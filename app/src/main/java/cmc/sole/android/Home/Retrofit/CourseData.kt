package cmc.sole.android.Home

import android.os.health.TimerStat
import cmc.sole.android.CourseTag.Categories

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
    var viewType: Int?
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

const val courseDetailNumber = 1
const val courseDetailLine = 2

const val locationImage = 1
const val locationAddImage = 2