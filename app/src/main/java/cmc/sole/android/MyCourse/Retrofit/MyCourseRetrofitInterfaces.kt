package cmc.sole.android.MyCourse.Retrofit

import cmc.sole.android.Home.MyPageUpdateResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface MyCourseRetrofitInterfaces {
    @GET("/api/histories")
    fun getMyCourseHistoryInfo(): Call<MyCourseHistoryInfoResponse>

    @POST("/api/histories/courses")
    fun getMyCourseHistory(
        @Query("courseId") courseId: Int,
        @Body myCourseHistoryRequest: MyCourseHistoryRequest
    ): Call<MyCourseHistoryResponse>

    @Multipart
    @POST("/api/courses/imageTest")
    fun imageTest(
        @Part thumbnailImg: List<MultipartBody.Part?>
    ): Call<String>

    @Multipart
    @POST("/api/courses")
    fun addMyCourses(
        @Part thumbnailImg: List<MultipartBody.Part?>,
        @Part courseRequestDto: MultipartBody.Part?
    ): Call<MyCourseAddResponse>

    @DELETE("/api/courses/{courseId}")
    fun deleteMyCourse(
        @Path("courseId") courseId: Int
    ): Call<Void>
}