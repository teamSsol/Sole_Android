package cmc.sole.android.MyCourse.Retrofit

import android.util.Log
import cmc.sole.android.DefaultResponse
import com.example.geeksasaeng.Utils.NetworkModule
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCourseService {
    private var myCourseService = NetworkModule.getInstance()?.create(MyCourseRetrofitInterfaces::class.java)

    private lateinit var myCourseHistoryInfoView: MyCourseHistoryInfoView
    private lateinit var myCourseHistoryView: MyCourseHistoryView
    private lateinit var myCourseNullTagHistoryView: MyCourseNullTagHistoryView
    private lateinit var myCourseAddView: MyCourseAddView
    private lateinit var imageTestView: ImageTestView
    private lateinit var myCourseUpdateView: MyCourseUpdateView
    private lateinit var myCourseDeleteView: MyCourseDeleteView
    private lateinit var myCourseReportView: MyCourseReportView
    private lateinit var courseScrapView: CourseScrapView

    fun setMyCourseHistoryInfoView(myCourseHistoryInfoView: MyCourseHistoryInfoView) {
        this.myCourseHistoryInfoView = myCourseHistoryInfoView
    }
    fun setMyCourseHistoryView(myCourseHistoryView: MyCourseHistoryView) {
        this.myCourseHistoryView = myCourseHistoryView
    }
    fun setMyCourseNullTagView(myCourseNullTagHistoryView: MyCourseNullTagHistoryView) {
        this.myCourseNullTagHistoryView = myCourseNullTagHistoryView
    }
    fun setMyCourseAddView(myCourseAddView: MyCourseAddView) {
        this.myCourseAddView = myCourseAddView
    }
    fun setImageTestView(imageTestView: ImageTestView) {
        this.imageTestView = imageTestView
    }
    fun setMyCourseUpdateView(myCourseUpdateView: MyCourseUpdateView) {
        this.myCourseUpdateView = myCourseUpdateView
    }
    fun setMyCourseDeleteView(myCourseDeleteView: MyCourseDeleteView) {
        this.myCourseDeleteView = myCourseDeleteView
    }
    fun setMyCourseReportView(myCourseReportView: MyCourseReportView) {
        this.myCourseReportView = myCourseReportView
    }
    fun setCourseScrapView(courseScrapView: CourseScrapView) {
        this.courseScrapView = courseScrapView
    }

    fun getMyCourseHistoryInfo() {
        myCourseService?.getMyCourseHistoryInfo()?.enqueue(object: Callback<MyCourseHistoryInfoResponse> {
            override fun onResponse(
                call: Call<MyCourseHistoryInfoResponse>,
                response: Response<MyCourseHistoryInfoResponse>
            ) {
                Log.d("API-TEST", "response.body = ${response.body()}")
                Log.d("API-TEST", "response.data = ${response.body()?.data}")
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        myCourseHistoryInfoView.setMyCourseHistoryInfoSuccessView(resp.data)
                    } else {
                        myCourseHistoryInfoView.setMyCourseHistoryInfoFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyCourseHistoryInfoResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-SERVICE-HISTORY-INFO-FAILURE", t)
            }
        })
    }

    fun getMyCourseHistory(courseId: Int?, myCourseHistoryRequest: MyCourseHistoryRequest) {
        myCourseService?.getMyCourseHistory(courseId, myCourseHistoryRequest)?.enqueue(object: Callback<MyCourseHistoryResponse> {
            override fun onResponse(
                call: Call<MyCourseHistoryResponse>,
                response: Response<MyCourseHistoryResponse>
            ) {
                Log.d("API-TEST", "getMyCourseHistory.body = ${response.body()}")
                Log.d("API-TEST", "getMyCourseHistory.data = ${response.body()?.data}")
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        myCourseHistoryView.setMyCourseHistorySuccessView(resp.data)
                    } else {
                        myCourseHistoryView.setMyCourseHistoryFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyCourseHistoryResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-SERVICE-HISTORY-FAILURE", t)
            }
        })
    }

    fun getMyCourseNullTagHistory(courseId: Int?) {
        myCourseService?.getMyCourseNullTagHistory(courseId)?.enqueue(object: Callback<MyCourseHistoryResponse> {
            override fun onResponse(
                call: Call<MyCourseHistoryResponse>,
                response: Response<MyCourseHistoryResponse>
            ) {
                Log.d("API-TEST", "getMyCourseNullTagHistory.body = ${response.body()}")
                Log.d("API-TEST", "getMyCourseNullTagHistory.data = ${response.body()?.data}")
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        myCourseNullTagHistoryView.setMyCourseNullTagHistorySuccessView(resp.data)
                    } else {
                        myCourseNullTagHistoryView.setMyCourseNullTagHistoryFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyCourseHistoryResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-SERVICE-NULL-TAG-HISTORY-FAILURE", t)
            }
        })
    }

    fun addMyCourse(thumbnailImg: List<MultipartBody.Part?>, courseRequestDto: MultipartBody.Part?) {
        myCourseService?.addMyCourses(thumbnailImg, courseRequestDto)?.enqueue(object: Callback<MyCourseAddResponse> {
            override fun onResponse(
                call: Call<MyCourseAddResponse>,
                response: Response<MyCourseAddResponse>
            ) {
                Log.d("WRITE-TEST", "addMyCourse response = $response")
                Log.d("WRITE-TEST", "addMyCourse response.body = ${response.body()}")
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        myCourseAddView.setMyCourseAddSuccessView(resp.data)
                    } else {
                        myCourseAddView.setMyCourseAddFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyCourseAddResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-ADD-COURSE-FAILURE", t)
            }
        })
    }

    fun updateMyCourse(courseId: Int, thumbnailImg: List<MultipartBody.Part?>, courseUpdateRequestDto: MultipartBody.Part?) {
        myCourseService?.updateMyCourse(courseId, thumbnailImg, courseUpdateRequestDto)?.enqueue(object: Callback<MyCourseUpdateResponse> {
            override fun onResponse(
                call: Call<MyCourseUpdateResponse>,
                response: Response<MyCourseUpdateResponse>
            ) {
                Log.d("WRITE-TEST", "addMyCourse response = $response")
                Log.d("WRITE-TEST", "addMyCourse response.body = ${response.body()}")
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        myCourseUpdateView.setMyCourseUpdateSuccessView()
                    } else {
                        myCourseUpdateView.setMyCourseUpdateFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<MyCourseUpdateResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-UPDATE-COURSE-FAILURE", t)
            }
        })
    }

//    fun imageTest(thumbnailImg: Map<String, List<MultipartBody.Part?>>) {
    fun imageTest(thumbnailImg: List<MultipartBody.Part?>) {
        myCourseService?.imageTest(thumbnailImg)?.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("WRITE-TEST", "img response = $response")
                Log.d("WRITE-TEST", "img response.body = ${response.body()}")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "IMAGE-TEST-FAILURE", t)
            }
        })
    }

    fun deleteCourse(courseId: Int) {
        myCourseService?.deleteMyCourse(courseId)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    myCourseDeleteView.setMyCourseDeleteSuccessView()
                } else {
                    myCourseDeleteView.setMyCourseDeleteFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-DELETE-FAILURE", t)
            }
        })
    }

    fun reportCourse(courseId: Int) {
        myCourseService?.reportCourse(courseId)?.enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if (response.code() == 200) {
                    myCourseReportView.setMyCourseReportSuccessView()
                } else {
                    myCourseReportView.setMyCourseReportFailureView()
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "MY-COURSE-REPORT-FAILURE", t)
            }
        })
    }

    fun courseScrap(courseId: Int) {
        myCourseService?.scrapCourse(courseId)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    courseScrapView.setCourseScrapSuccessView()
                } else {
                    courseScrapView.setCourseScrapFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "COURSE-SCRAP-FAILURE", t)
            }
        })
    }
}