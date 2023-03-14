package cmc.sole.android.Home.Retrofit

import android.util.Log
import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.HomePopularResponse
import cmc.sole.android.getAccessToken
import com.example.geeksasaeng.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService {
    private val homeService = NetworkModule.getInstance()?.create(HomeRetrofitInterface::class.java)

    private lateinit var homePopularCourseView: HomePopularCourseView
    private lateinit var homeDefaultCourseView: HomeDefaultCourseView

    fun setHomePopularCourseView(homePopularCourseView: HomePopularCourseView) {
        this.homePopularCourseView = homePopularCourseView
    }
    fun setHomeDefaultCourseView(homeDefaultCourseView: HomeDefaultCourseView) {
        this.homeDefaultCourseView = homeDefaultCourseView
    }

    fun getHomePopularCourse() {
        homeService?.getHomePopularCourse()?.enqueue(object: Callback<HomePopularResponse> {
            override fun onResponse(
                call: Call<HomePopularResponse>,
                response: Response<HomePopularResponse>
            ) {
                if (response.code() == 200) {
                    val homePopularResponse = response.body()
                    if (homePopularResponse?.success == true) {
                        homePopularCourseView.homePopularCourseSuccessView(homePopularResponse)
                    } else {
                        homePopularCourseView.homePopularCourseFailureView()
                    }

                }
            }
            override fun onFailure(call: Call<HomePopularResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-POPULAR-FAILURE", t)
            }
        })
    }

    fun getHomeDefaultCourse(courseId: Int, searchWord: String) {
        homeService?.getHomeDefaultCourse(courseId, searchWord)?.enqueue(object: Callback<HomeDefaultResponse> {
            override fun onResponse(
                call: Call<HomeDefaultResponse>,
                response: Response<HomeDefaultResponse>
            ) {
                Log.d("API-TEST", "response = $response")
                if (response.code() == 200) {
                    val homeDefaultResponse = response.body()
                    if (homeDefaultResponse?.success == true) {
                        homeDefaultCourseView.homeDefaultCourseSuccessView(homeDefaultResponse)
                    } else {
                        homeDefaultCourseView.homeDefaultCourseFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<HomeDefaultResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-DEFAULT-FAILURE", t)
            }
        })
    }
}