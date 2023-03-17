package cmc.sole.android.Home.Retrofit

import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.HomePopularResponse
import cmc.sole.android.Home.MyPageInfoResponse

interface HomePopularCourseView {
    fun homePopularCourseSuccessView(homePopularResponse: HomePopularResponse)
    fun homePopularCourseFailureView()
}

interface HomeDefaultCourseView {
    fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse)
    fun homeDefaultCourseFailureView()
}

// MEMO: 마이페이지
interface MyPageInfoView {
    fun myPageInfoSuccessView(myPageInfoResponse: MyPageInfoResponse)
    fun myPageInfoFailureView()
}