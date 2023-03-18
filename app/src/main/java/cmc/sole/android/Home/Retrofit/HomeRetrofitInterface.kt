package cmc.sole.android.Home.Retrofit

import cmc.sole.android.DefaultResponse
import cmc.sole.android.Home.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

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

    @Multipart
    @PUT("/api/mypage")
    fun updateMyPageInfo(
        @Part mypageRequestDto: MultipartBody.Part,
        @Part multipartFile: MultipartBody.Part?
    ): Call<MyPageUpdateResponse>

    // MEMO: 마이페이지 알림
    @GET("/api/mypage/notification")
    fun getMyPageNotification(): Call<MyPageNotificationResponse>

    @PUT("/api/mypage/notification")
    fun updateMyPageNotification(
        @Body myPageNotificationRequest: MyPageNotificationUpdateRequest
    ): Call<MyPageNotificationUpdateResponse>

    @GET("/api/mypage/notification/histories")
    fun getMyPageNotificationHistory(): Call<MyPageNotificationHistoryResponse>

    // MEMO: 마이페이지 공지사항
    @GET("/api/notices")
    fun getMyPageNotice(): Call<MyPageNoticeResponse>

    @POST("/api/notices")
    fun addMyPageNotice(
        @Body myPageNoticeAddRequest: MyPageNoticeAddRequest
    ): Call<MyPageNoticeAddResponse>

    @PUT("/api/notices/{noticeId}")
    fun updateMyPageNotice(
        @Path("noticeId") noticeId: Int,
        @Body myPageNoticeUpdateRequest: MyPageNoticeAddRequest
    ): Call<MyPageNoticeAddResponse>

    // MEMO: 회원탈퇴
    @DELETE("api/mypage/quit")
    fun quitMember(): Call<DefaultResponse>
}