package cmc.sole.android.MyCourse.Retrofit

import retrofit2.Call
import retrofit2.http.GET

interface MyCourseRetrofitInterfaces {
    @GET("/api/histories")
    fun getMyCourseHistoryInfo(): Call<MyCourseHistoryResponse>
}