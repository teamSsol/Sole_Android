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

interface MyCourseAddView {
    fun setMyCourseAddSuccessView(myCourseAddResult: MyCourseAddResult)
    fun setMyCourseAddFailureView()
}

interface ImageTestView {
    fun setImageTestSuccessView()
    fun setImageTestFailureView()
}

interface MyCourseDeleteView {
    fun setMyCourseDeleteSuccessView()
    fun setMyCourseDeleteFailureView()
}