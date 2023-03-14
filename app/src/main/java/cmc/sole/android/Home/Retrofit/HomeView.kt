package cmc.sole.android.Home.Retrofit

import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.HomePopularResponse

interface HomePopularCourseView {
    fun homePopularCourseSuccessView(homePopularResponse: HomePopularResponse)
    fun homePopularCourseFailureView()
}

interface HomeDefaultCourseView {
    fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse)
    fun homeDefaultCourseFailureView()
}