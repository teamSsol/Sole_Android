package cmc.sole.android.MyCourse.Retrofit

import android.support.v4.os.IResultReceiver.Default
import cmc.sole.android.Home.DefaultCourse

interface MyCourseHistoryInfoView {
    fun setMyCourseHistoryInfoSuccessView(myCourseHistoryResult: MyCourseHistoryInfoResult)
    fun setMyCourseHistoryInfoFailureView()
}

interface MyCourseHistoryView {
    fun setMyCourseHistorySuccessView(myCourseHistoryResult: ArrayList<DefaultCourse>)
    fun setMyCourseHistoryFailureView()
}