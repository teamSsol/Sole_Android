package cmc.sole.android.Scrap.Retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScrapRetrofitInterfaces {
    @GET("/api/scraps")
    fun getScrapFolder(): Call<ScrapFolderDataResponse>

    @POST("/api/scraps")
    fun addScrapFolder(
        @Body scrapFolderAddRequest: ScrapFolderAddRequest
    ): Call<ScrapFolderAddResponse>

    @GET("api/scraps/{scrapFolderId}")
    fun getScrapCourse(
        @Path("scrapFolderId") scrapFolderId: Int
    ): Call<ScrapCourseResponse>

    @DELETE("/api/scraps/{scrapFolderId}/{courseId}")
    fun deleteScrapCourse(
        @Path("scrapFolderId") scrapFolderId: Int,
        @Path("courseId") courseId: Int
    ): Call<ScrapCourseDeleteResponse>
}