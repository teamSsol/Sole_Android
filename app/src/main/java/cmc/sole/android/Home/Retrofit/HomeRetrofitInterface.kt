package cmc.sole.android.Home.Retrofit

import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.DefaultResponse
import cmc.sole.android.Home.*
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryRequest
import cmc.sole.android.TagSettingRequest
import cmc.sole.android.Utils.Region
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {
    @GET("/api/courses/currentGps")
    fun getMyCurrentGPS(): Call<DefaultResponse>

    @PATCH("/api/courses/currentGps")
    fun updateMyCurrentGPS(
        @Body homeCurrentGPSRequest: HomeCurrentGPSRequest
    ): Call<HomeCurrentGPSResponse>

    @PATCH("/api/courses/favCategory")
    fun updateCategories(
        @Body tagSettingRequest: TagSettingRequest
    ): Call<HomeCategoriesResponse>

    @GET("/api/courses/favCategory")
    fun getCategories(): Call<HomeCategoriesResponse>

    @GET("/api/courses/recommend")
    fun getHomePopularCourse(): Call<HomePopularResponse>

    @GET("/api/courses")
    fun getHomeDefaultCourse(
        @Query("courseId") courseId: Int? = null,
        @Query("searchWord") searchWord: String = "",
        @Query("placeCategories") placeCategories: HashSet<Categories>? = null,
        @Query("withCategories") withCategories: HashSet<Categories>? = null,
        @Query("transCategories") transCategories: HashSet<Categories>? = null,
        @Query("regions") regions: HashSet<Region>? = null
    ): Call<HomeDefaultResponse>

    @GET("/api/courses/{courseId}")
    fun getHomeDetailCourse(
        @Path("courseId") courseId: Int?
    ): Call<HomeCourseDetailResponse>

    @POST("/api/courses/{courseId}/scrap")
    fun scrapAddAndCancel(
        @Path("courseId") courseId: Int
    ): Call<Void>

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
    fun quitMember(): Call<Void>
    
    // MEMO: 스크랩 등록/취소
    @POST("/api/courses/{courseId}/scrap")
    fun scrapOnOff(
        @Path("courseId") courseId: Int
    ): Call<Void>
}