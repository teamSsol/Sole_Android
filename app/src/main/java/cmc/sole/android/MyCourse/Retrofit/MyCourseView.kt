package cmc.sole.android.MyCourse.Retrofit

import cmc.sole.android.Home.DefaultCourse

interface MyCourseHistoryInfoView {
    fun setMyCourseHistoryInfoSuccessView(myCourseHistoryResult: MyCourseHistoryInfoResult)
    fun setMyCourseHistoryInfoFailureView()
}

interface MyCourseHistoryView {
    fun setMyCourseHistorySuccessView(myCourseHistoryResult: ArrayList<DefaultCourse>)
    fun setMyCourseHistoryFailureView()
}

interface MyCourseNullTagHistoryView {
    fun setMyCourseNullTagHistorySuccessView(myCourseHistoryResult: ArrayList<DefaultCourse>)
    fun setMyCourseNullTagHistoryFailureView()
}

interface MyCourseAddView {
    fun setMyCourseAddSuccessView(myCourseAddResult: MyCourseAddResult)
    fun setMyCourseAddFailureView()
}

interface ImageTestView {
    fun setImageTestSuccessView()
    fun setImageTestFailureView()
}

interface MyCourseUpdateView {
    fun setMyCourseUpdateSuccessView()
    fun setMyCourseUpdateFailureView()
}

interface MyCourseDeleteView {
    fun setMyCourseDeleteSuccessView()
    fun setMyCourseDeleteFailureView()
}

interface MyCourseReportView {
    fun setMyCourseReportSuccessView()
    fun setMyCourseReportFailureView()
}

interface CourseScrapView {
    fun setCourseScrapSuccessView()
    fun setCourseScrapFailureView()
}