package cmc.sole.android.Home.Retrofit

import android.util.Log
import cmc.sole.android.Home.*
import cmc.sole.android.getAccessToken
import com.example.geeksasaeng.Utils.NetworkModule
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService {
    private val homeService = NetworkModule.getInstance()?.create(HomeRetrofitInterface::class.java)

    private lateinit var homePopularCourseView: HomePopularCourseView
    private lateinit var homeDefaultCourseView: HomeDefaultCourseView
    private lateinit var myPageInfoView: MyPageInfoView
    private lateinit var myPageInfoUpdateView: MyPageInfoUpdateView
    private lateinit var myPageNotificationView: MyPageNotificationView
    private lateinit var myPageNotificationUpdateView: MyPageNotificationUpdateView
    private lateinit var myPageNotificationHistoryView: MyPageNotificationHistoryView
    private lateinit var myPageMemberQuitView: MyPageMemberQuitView

    fun setHomePopularCourseView(homePopularCourseView: HomePopularCourseView) {
        this.homePopularCourseView = homePopularCourseView
    }
    fun setHomeDefaultCourseView(homeDefaultCourseView: HomeDefaultCourseView) {
        this.homeDefaultCourseView = homeDefaultCourseView
    }
    fun setMyPageInfoView(myPageInfoView: MyPageInfoView) {
        this.myPageInfoView = myPageInfoView
    }
    fun setMyPageInfoUpdateView(myPageInfoUpdateView: MyPageInfoUpdateView) {
        this.myPageInfoUpdateView = myPageInfoUpdateView
    }
    fun setMyPageNotificationView(myPageNotificationView: MyPageNotificationView) {
        this.myPageNotificationView = myPageNotificationView
    }
    fun setMyPageNotificationUpdateView(myPageNotificationUpdateView: MyPageNotificationUpdateView) {
        this.myPageNotificationUpdateView = myPageNotificationUpdateView
    }
    fun setMyPageNotificationHistoryView(myPageNotificationHistoryView: MyPageNotificationHistoryView) {
        this.myPageNotificationHistoryView = myPageNotificationHistoryView
    }
    fun setMyPageMemberQuitView(myPageMemberQuitView: MyPageMemberQuitView) {
        this.myPageMemberQuitView = myPageMemberQuitView
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

    fun getMyPageInfo() {
        homeService?.getMyPageInfo()?.enqueue(object: Callback<MyPageInfoResponse> {
            override fun onResponse(
                call: Call<MyPageInfoResponse>,
                response: Response<MyPageInfoResponse>
            ) {
                if (response.code() == 200) {
                    val myPageResponse = response.body()
                    if (myPageResponse?.success == true) {
                        myPageInfoView.myPageInfoSuccessView(myPageResponse.data)
                    } else {
                        myPageInfoView.myPageInfoFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageInfoResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-MY-PAGE-FAILURE", t)
            }
        })
    }

    fun myPageInfoUpdate(mypageRequestDto: MultipartBody.Part, multipartFile: MultipartBody.Part?) {
        homeService?.updateMyPageInfo(mypageRequestDto, multipartFile)?.enqueue(object: Callback<MyPageUpdateResponse> {
            override fun onResponse(
                call: Call<MyPageUpdateResponse>,
                response: Response<MyPageUpdateResponse>
            ) {
                if (response.code() == 200) {
                    val myPageUpdateResponse = response.body()
                    if (myPageUpdateResponse?.success == true) {
                        myPageInfoUpdateView.myPageInfoUpdateSuccessView(myPageUpdateResponse.data)
                    } else {
                        myPageInfoUpdateView.myPageInfoUpdateFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageUpdateResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-UPDATE-MY-PAGE-FAILURE", t)
            }
        })
    }

    fun getMyPageNotification() {
        homeService?.getMyPageNotification()?.enqueue(object: Callback<MyPageNotificationResponse> {
            override fun onResponse(
                call: Call<MyPageNotificationResponse>,
                response: Response<MyPageNotificationResponse>
            ) {
                if (response.code() == 200) {
                    val myPageNotificationResponse = response.body()
                    if (myPageNotificationResponse?.success == true) {
                        myPageNotificationView.myPageNotificationSuccessView(myPageNotificationResponse.data)
                    } else {
                        myPageNotificationView.myPageNotificationFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageNotificationResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-NOTIFICATION-FAILURE", t)
            }
        })
    }

    fun myPageNotificationUpdate(myPageNotificationUpdate: MyPageNotificationUpdateRequest) {
        homeService?.updateMyPageNotification(myPageNotificationUpdate)?.enqueue(object: Callback<MyPageNotificationUpdateResponse> {
            override fun onResponse(
                call: Call<MyPageNotificationUpdateResponse>,
                response: Response<MyPageNotificationUpdateResponse>
            ) {
                if (response.code() == 200) {
                    val myPageNotificationUpdateResponse = response.body()
                    if (myPageNotificationUpdateResponse?.success == true) {
                        myPageNotificationUpdateView.myPageNotificationUpdateSuccessView(myPageNotificationUpdateResponse.data)
                    } else {
                        myPageNotificationUpdateView.myPageNotificationUpdateFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageNotificationUpdateResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-NOTIFICATION-UPDATE-FAILURE", t)
            }
        })
    }

    fun getMyPageNotificationHistory() {
        homeService?.getMyPageNotificationHistory()?.enqueue(object: Callback<MyPageNotificationHistoryResponse> {
            override fun onResponse(
                call: Call<MyPageNotificationHistoryResponse>,
                response: Response<MyPageNotificationHistoryResponse>
            ) {
                Log.d("API-TEST", "notification history response = ${response.body()}")
                Log.d("API-TEST", "notification history body = ${response.body()?.data}")
                if (response.code() == 200) {
                    val myPageNotificationHistoryResponse = response.body()
                    if (myPageNotificationHistoryResponse?.success == true) {
                        myPageNotificationHistoryView.myPageNotificationHistorySuccessView(myPageNotificationHistoryResponse.data)
                    } else {
                        myPageNotificationHistoryView.myPageNotificationHistoryFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageNotificationHistoryResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-NOTIFICATION-HISTORY-FAILURE", t)
            }
        })
    }

    fun deleteMember() {
        homeService?.quitMember()?.enqueue(object: Callback<MyPageMemberQuitResponse> {
            override fun onResponse(
                call: Call<MyPageMemberQuitResponse>,
                response: Response<MyPageMemberQuitResponse>
            ) {
                Log.d("API-TEST", "quit member response = ${response.body()}")
                Log.d("API-TEST", "quit member body = ${response.body()?.data}")
                if (response.code() == 200) {
                    val myPageMemberQuitResponse = response.body()
                    if (myPageMemberQuitResponse?.success == true) {
                        myPageMemberQuitView.myPageMemberQuitSuccessView()
                    } else {
                        myPageMemberQuitView.myPageMemberQuitFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageMemberQuitResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-MEMBER-QUIT-FAILURE", t)
            }
        })
    }
}