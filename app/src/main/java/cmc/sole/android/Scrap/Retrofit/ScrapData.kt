package cmc.sole.android.Scrap.Retrofit

import cmc.sole.android.CourseTag.Categories

data class ScrapFolderDataResponse(
    var data: ArrayList<ScrapFolderDataResult>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class ScrapFolderDataResult(
    var scrapFolderId: Int,
    var scrapFolderName: String,
    var viewType: Int = 0
)

data class ScrapFolderAddRequest(
    var scrapFolderName: String
)

data class ScrapFolderAddResponse(
    var data: ScrapFolderAddResult,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class ScrapFolderAddResult(
    var scrapFolderId: Int,
    var scrapFolderName: String
)

data class ScrapCourseResponse(
    var data: ArrayList<ScrapCourseResult>,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

data class ScrapCourseResult(
    var address: String,
    var categories: Set<Categories>,
    var courseId: Int,
    var distance: Int,
    var duration: Int,
    var like: Boolean,
    var thumbnailImg: String,
    var title: String,
    var checkMode: Boolean = false,
    var isChecked: Boolean = false
    // var viewType: Int?
)

data class ScrapCourseDeleteResponse(
    var data: String,
    var status: Int,
    var success: Boolean,
    var timestamp: String
)

const val defaultFolder = 1
const val addFolder = 2

//const val checkCourse = 1
//const val unCheckCourse = 2