package cmc.sole.android.MyCourse.Retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyCourseRetrofitInterfaces {
    @GET("/api/histories")
    fun getMyCourseHistoryInfo(): Call<MyCourseHistoryInfoResponse>

    @POST("/api/histories/courses")
    fun getMyCourseHistory(
        @Query("courseId") courseId: Int,
        @Body myCourseHistoryRequest: MyCourseHistoryRequest
    ): Call<MyCourseHistoryResponse>
}