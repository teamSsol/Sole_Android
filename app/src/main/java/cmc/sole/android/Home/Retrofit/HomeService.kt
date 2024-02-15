package cmc.sole.android.Home.Retrofit

import android.util.Log
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.DefaultResponse
import cmc.sole.android.ErrorResponse
import cmc.sole.android.Home.*
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryRequest
import cmc.sole.android.TagSettingRequest
import cmc.sole.android.Utils.Region
import com.example.geeksasaeng.Utils.NetworkModule
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService {
    private val homeService = NetworkModule.getInstance()?.create(HomeRetrofitInterface::class.java)

    private lateinit var homeGetCurrentGPSView: HomeGetCurrentGPSView
    private lateinit var homeUpdateCurrentGPSView: HomeUpdateCurrentGPSView
    private lateinit var homeCategoriesUpdateView: HomeCategoriesUpdateView
    private lateinit var homeGetCategoriesView: HomeGetCategoriesView
    private lateinit var homePopularCourseView: HomePopularCourseView
    private lateinit var homeDefaultCourseView: HomeDefaultCourseView
    private lateinit var homeFilterCourseView: HomeFilterCourseView
    private lateinit var homeCourseDetailView: HomeCourseDetailView
    private lateinit var homeScrapAddAndCancelView: HomeScrapAddAndCancelView
    private lateinit var myPageInfoView: MyPageInfoView
    private lateinit var myPageInfoUpdateView: MyPageInfoUpdateView
    private lateinit var myPageNotificationView: MyPageNotificationView
    private lateinit var myPageNotificationUpdateView: MyPageNotificationUpdateView
    private lateinit var myPageNotificationHistoryView: MyPageNotificationHistoryView
    private lateinit var myPageNoticeView: MyPageNoticeView
    private lateinit var myPageNoticeAddView: MyPageNoticeAddView
    private lateinit var myPageNoticeUpdateView: MyPageNoticeUpdateView
    private lateinit var myPageMemberQuitView: MyPageMemberQuitView

    fun setHomeGetCurrentGPSView(homeGetCurrentGPSView: HomeGetCurrentGPSView) {
        this.homeGetCurrentGPSView = homeGetCurrentGPSView
    }
    fun setHomeUpdateCurrentGPSView(homeUpdateCurrentGPSView: HomeUpdateCurrentGPSView) {
        this.homeUpdateCurrentGPSView = homeUpdateCurrentGPSView
    }
    fun setHomeCategoriesUpdateView(homeCategoriesUpdateView: HomeCategoriesUpdateView) {
        this.homeCategoriesUpdateView = homeCategoriesUpdateView
    }
    fun setHomeGetCategoriesView(homeGetCategoriesView: HomeGetCategoriesView) {
        this.homeGetCategoriesView = homeGetCategoriesView
    }
    fun setHomePopularCourseView(homePopularCourseView: HomePopularCourseView) {
        this.homePopularCourseView = homePopularCourseView
    }
    fun setHomeDefaultCourseView(homeDefaultCourseView: HomeDefaultCourseView) {
        this.homeDefaultCourseView = homeDefaultCourseView
    }
    fun setHomeFilterCourseView(homeFilterCourseView: HomeFilterCourseView) {
        this.homeFilterCourseView = homeFilterCourseView
    }
    fun setHomeCourseDetailView(homeCourseDetailView: HomeCourseDetailView) {
        this.homeCourseDetailView = homeCourseDetailView
    }
    fun setHomeScrapAddAndCancelView(homeScrapAddAndCancelView: HomeScrapAddAndCancelView) {
        this.homeScrapAddAndCancelView = homeScrapAddAndCancelView
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
    fun setMyPageNoticeView(myPageNoticeView: MyPageNoticeView) {
        this.myPageNoticeView = myPageNoticeView
    }
    fun setMyPageNoticeAddView(myPageNoticeAddView: MyPageNoticeAddView) {
        this.myPageNoticeAddView = myPageNoticeAddView
    }
    fun setMyPageNoticeUpdateView(myPageNoticeUpdateView: MyPageNoticeUpdateView) {
        this.myPageNoticeUpdateView = myPageNoticeUpdateView
    }
    fun setMyPageMemberQuitView(myPageMemberQuitView: MyPageMemberQuitView) {
        this.myPageMemberQuitView = myPageMemberQuitView
    }

    fun getCurrentGPS() {
        homeService?.getMyCurrentGPS()?.enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        homeGetCurrentGPSView.homeGetCurrentGPSSuccessView(resp.data)
                    } else {
                        homeGetCurrentGPSView.homeGetCurrentGPSFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-CURRENT-GPS-FAILURE", t)
            }
        })
    }

    fun updateCurrentGPS(homeCurrentGPSRequest: HomeCurrentGPSRequest) {
        homeService?.updateMyCurrentGPS(homeCurrentGPSRequest)?.enqueue(object: Callback<HomeCurrentGPSResponse> {
            override fun onResponse(
                call: Call<HomeCurrentGPSResponse>,
                response: Response<HomeCurrentGPSResponse>
            ) {
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        homeUpdateCurrentGPSView.homeUpdateCurrentGPSSuccessView(resp.data)
                    } else {
                        homeUpdateCurrentGPSView.homeUpdateCurrentGPSFailureView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                }
            }
            override fun onFailure(call: Call<HomeCurrentGPSResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-UPDATE-CURRENT-GPS-FAILURE", t)
            }
        })
    }

    fun updateCategories(tagSettingRequest: TagSettingRequest) {
        homeService?.updateCategories(tagSettingRequest)?.enqueue(object: Callback<HomeCategoriesResponse> {
            override fun onResponse(
                call: Call<HomeCategoriesResponse>,
                response: Response<HomeCategoriesResponse>
            ) {
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        homeCategoriesUpdateView.homeCategoriesUpdateSuccessView()
                    } else {
                        homeCategoriesUpdateView.homeCategoriesUpdateFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<HomeCategoriesResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-UPDATE-CATEGORIES-FAILURE", t)
            }
        })
    }

    fun getCategories() {
        homeService?.getCategories()?.enqueue(object: Callback<HomeCategoriesResponse> {
            override fun onResponse(
                call: Call<HomeCategoriesResponse>,
                response: Response<HomeCategoriesResponse>
            ) {
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        homeGetCategoriesView.homeGetCategoriesUpdateSuccessView(response.body()!!.data)
                    } else {
                        homeGetCategoriesView.homeGetCategoriesUpdateFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<HomeCategoriesResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-CATEGORIES-FAILURE", t)
            }
        })
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

    fun getHomeDefaultCourse(courseId: Int?, searchWord: String) {
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

    fun getHomeFilterCourse(courseId: Int?, searchWord: String, placeCategories: HashSet<Categories>?, withCategories: HashSet<Categories>?, transCategories: HashSet<Categories>?, regions: HashSet<Region>?) {
        homeService?.getHomeFilterCourse(courseId, searchWord, placeCategories, withCategories, transCategories, regions)?.enqueue(object: Callback<HomeDefaultResponse> {
            override fun onResponse(
                call: Call<HomeDefaultResponse>,
                response: Response<HomeDefaultResponse>
            ) {
                if (response.code() == 200) {
                    val homeFilterResponse = response.body()
                    if (homeFilterResponse?.success == true) {
                        homeFilterCourseView.homeFilterCourseSuccessView(homeFilterResponse)
                    } else {
                        homeFilterCourseView.homeFilterCourseFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<HomeDefaultResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-DEFAULT-FAILURE", t)
            }
        })
    }

    fun getHomeDetailCourse(courseId: Int?) {
        homeService?.getHomeDetailCourse(courseId)?.enqueue(object: Callback<HomeCourseDetailResponse> {
            override fun onResponse(
                call: Call<HomeCourseDetailResponse>,
                response: Response<HomeCourseDetailResponse>
            ) {
                if (response.code() == 200) {
                    val homeDetailResponse = response.body()
                    if (homeDetailResponse?.success == true) {
                        homeCourseDetailView.homeCourseDetailSuccessView(homeDetailResponse.data)
                    } else {
                        homeCourseDetailView.homeCourseDetailFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<HomeCourseDetailResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-GET-COURSE-DETAIL-FAILURE", t)
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

    // MEMO: 마이페이지 공지사항 조회
    fun getMyPageNotice() {
        homeService?.getMyPageNotice()?.enqueue(object: Callback<MyPageNoticeResponse> {
            override fun onResponse(
                call: Call<MyPageNoticeResponse>,
                response: Response<MyPageNoticeResponse>
            ) {
                if (response.code() == 200) {
                    val myPageNoticeResponse = response.body()
                    if (myPageNoticeResponse?.success == true) {
                        myPageNoticeView.myPageNoticeSuccessView(myPageNoticeResponse.data)
                    } else {
                        myPageNoticeView.myPageNoticeFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageNoticeResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-NOTICE-FAILURE", t)
            }
        })
    }

    // MEMO: 공지사항 등록
    // MEMO: 필요한 곳에 사용!
    fun addMyPageNotice(myPageNoticeAddRequest: MyPageNoticeAddRequest) {
        homeService?.addMyPageNotice(myPageNoticeAddRequest)?.enqueue(object: Callback<MyPageNoticeAddResponse> {
            override fun onResponse(
                call: Call<MyPageNoticeAddResponse>,
                response: Response<MyPageNoticeAddResponse>
            ) {
                if (response.code() == 200) {
                    val myPageNoticeAddResponse = response.body()
                    if (myPageNoticeAddResponse?.success == true) {
                        myPageNoticeAddView.myPageNoticeAddSuccessView(myPageNoticeAddResponse.data)
                    } else {
                        myPageNoticeAddView.myPageNoticeAddFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageNoticeAddResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-NOTICE-ADD-FAILURE", t)
            }
        })
    }

    // MEMO: 공지사항 수정
    // MEMO: 필요한 곳에 사용!
    fun updateMyPageNotice(noticeId: Int, myPageNoticeUpdateRequest: MyPageNoticeAddRequest) {
        homeService?.updateMyPageNotice(noticeId, myPageNoticeUpdateRequest)?.enqueue(object: Callback<MyPageNoticeAddResponse> {
            override fun onResponse(
                call: Call<MyPageNoticeAddResponse>,
                response: Response<MyPageNoticeAddResponse>
            ) {
                if (response.code() == 200) {
                    val myPageNoticeUpdateResponse = response.body()
                    if (myPageNoticeUpdateResponse?.success == true) {
                        myPageNoticeUpdateView.myPageNoticeUpdateSuccessView(myPageNoticeUpdateResponse.data)
                    } else {
                        myPageNoticeUpdateView.myPageNoticeUpdateFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyPageNoticeAddResponse>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-NOTICE-UPDATE-FAILURE", t)
            }
        })
    }

    fun deleteMember() {
        homeService?.quitMember()?.enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.code() == 200) {
                    myPageMemberQuitView.myPageMemberQuitSuccessView()
                } else {
                    myPageMemberQuitView.myPageMemberQuitFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("HOME-SERVICE", "HOME-SERVICE-MY-PAGE-MEMBER-QUIT-FAILURE", t)
            }
        })
    }

    // MEMO: 스크랩 등록/취소
    fun scrapAddAndCancel(courseId: Int, scrapFolderId: Int?) {
        homeService?.scrapAddAndCancel(courseId, scrapFolderId)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API-TEST", "${response.code()}\n${response.body()}")
                if (response.code() == 200) {
                    homeScrapAddAndCancelView.homeScrapAddAndCancelSuccessView()
                } else {
                    homeScrapAddAndCancelView.homeScrapAddAndCancelFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API-TEST", "HOME-SERVICE-SCRAP-ADD-CANCEL-FAILURE", t)
            }
        })
    }
}