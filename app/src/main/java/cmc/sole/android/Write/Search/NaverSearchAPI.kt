package cmc.sole.android.Write.Search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NaverSearchAPI {
    @GET("search/{path}")
    fun searchKeyword(
        @Header("X-Naver-Client-Id") naverClientId: String,
        @Header("X-Naver-Client-Secret") naverClientSecret: String,
        @Path("path") path: String,
        @Query("query") query: String,
        @Query("display") display: Int
    ): Call<SearchNaverData>
}