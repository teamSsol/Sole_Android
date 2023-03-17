package cmc.sole.android.Home.Retrofit

import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.HomePopularResponse
import cmc.sole.android.Home.MyPageInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeRetrofitInterface {
    @GET("/api/courses/recommend")
    fun getHomePopularCourse(): Call<HomePopularResponse>

    @GET("/api/courses")
    fun getHomeDefaultCourse(
        @Query("courseId") courseId: Int,
        @Query("searchWord") searchWord: String
    ): Call<HomeDefaultResponse>

    // MEMO: 마이페이지
    @GET("/api/mypage")
    fun getMyPageInfo(): Call<MyPageInfoResponse>
}