package cmc.sole.android.MyCourse.Retrofit

import android.util.Log
import com.example.geeksasaeng.Utils.NetworkModule
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCourseService {
    private var myCourseService = NetworkModule.getInstance()?.create(MyCourseRetrofitInterfaces::class.java)

    private lateinit var myCourseHistoryInfoView: MyCourseHistoryInfoView
    private lateinit var myCourseHistoryView: MyCourseHistoryView
    private lateinit var myCourseAddView: MyCourseAddView
    private lateinit var imageTestView: ImageTestView
    private lateinit var myCourseDeleteView: MyCourseDeleteView

    fun setMyCourseHistoryInfoView(myCourseHistoryInfoView: MyCourseHistoryInfoView) {
        this.myCourseHistoryInfoView = myCourseHistoryInfoView
    }
    fun setMyCourseHistoryView(myCourseHistoryView: MyCourseHistoryView) {
        this.myCourseHistoryView = myCourseHistoryView
    }
    fun setMyCourseAddView(myCourseAddView: MyCourseAddView) {
        this.myCourseAddView = myCourseAddView
    }
    fun setImageTestView(imageTestView: ImageTestView) {
        this.imageTestView = imageTestView
    }
    fun setMyCourseDeleteView(myCourseDeleteView: MyCourseDeleteView) {
        this.myCourseDeleteView = myCourseDeleteView
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

    fun getMyCourseHistory(courseId: Int, myCourseHistoryRequest: MyCourseHistoryRequest) {
        myCourseService?.getMyCourseHistory(courseId, myCourseHistoryRequest)?.enqueue(object: Callback<MyCourseHistoryResponse> {
            override fun onResponse(
                call: Call<MyCourseHistoryResponse>,
                response: Response<MyCourseHistoryResponse>
            ) {
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

    fun deleteTest(courseId: Int) {
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
}