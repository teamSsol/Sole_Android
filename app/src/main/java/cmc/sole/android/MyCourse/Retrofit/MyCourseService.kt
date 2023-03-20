package cmc.sole.android.MyCourse.Retrofit

import android.util.Log
import cmc.sole.android.DefaultResponse
import com.example.geeksasaeng.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCourseService {
    private var myCourseService = NetworkModule.getInstance()?.create(MyCourseRetrofitInterfaces::class.java)

    private lateinit var myCourseHistoryInfoView: MyCourseHistoryInfoView
    private lateinit var myCourseHistoryView: MyCourseHistoryView

    fun setMyCourseHistoryInfoView(myCourseHistoryInfoView: MyCourseHistoryInfoView) {
        this.myCourseHistoryInfoView = myCourseHistoryInfoView
    }
    fun setMyCourseHistoryView(myCourseHistoryView: MyCourseHistoryView) {
        this.myCourseHistoryView = myCourseHistoryView
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
}