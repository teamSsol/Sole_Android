package cmc.sole.android.Follow.Retrofit

import cmc.sole.android.DefaultResponse
import cmc.sole.android.Follow.FollowCourseResponse
import cmc.sole.android.Follow.FollowListResponse
import cmc.sole.android.Follow.FollowUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowRetrofitInterfaces {
    @GET("/api/follows")
    fun getFollowCourse(): Call<FollowCourseResponse>

    @GET("/api/follows/followers")
    fun getFollowersList(): Call<FollowListResponse>

    @GET("/api/follows/followings")
    fun getFollowingsList(): Call<FollowListResponse>

    @POST("/api/follows/follow/{toMemberId}")
    fun followUnfollow(
        @Path("toMemberId") toMemberId: Int
    ): Call<DefaultResponse>

    @GET("/api/follows/{followInfoMemberSocialId}")
    fun getFollowUserInfo(
        @Path("followInfoMemberSocialId") followInfoMemberSocialId: String,
        @Query("courseId") courseId: Int?
    ): Call<FollowUserInfoResponse>
}