package cmc.sole.android.Scrap.Retrofit

import android.util.Log
import cmc.sole.android.DefaultResponse
import cmc.sole.android.Home.HomePopularResponse
import cmc.sole.android.Home.Retrofit.HomeRetrofitInterface
import com.example.geeksasaeng.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScrapService {
    private val scrapService = NetworkModule.getInstance()?.create(ScrapRetrofitInterfaces::class.java)

    private lateinit var scrapFolderView: ScrapFolderView
    private lateinit var scrapFolderAddView: ScrapFolderAddView
    private lateinit var scrapFolderDeleteView: ScrapFolderDeleteView
    private lateinit var scrapCourseView: ScrapCourseView
    private lateinit var scrapCourseDeleteView: ScrapCourseDeleteView

    fun setScrapFolderView(scrapFolderView: ScrapFolderView) {
        this.scrapFolderView = scrapFolderView
    }
    fun setScrapFolderAddView(scrapFolderAddView: ScrapFolderAddView) {
        this.scrapFolderAddView = scrapFolderAddView
    }
    fun setScrapFolderDeleteView(scrapFolderDeleteView: ScrapFolderDeleteView) {
        this.scrapFolderDeleteView = scrapFolderDeleteView
    }
    fun setScrapCourseView(scrapCourseView: ScrapCourseView) {
        this.scrapCourseView = scrapCourseView
    }
    fun setScrapCourseDeleteView(scrapCourseDeleteView: ScrapCourseDeleteView) {
        this.scrapCourseDeleteView = scrapCourseDeleteView
    }

    fun getScrapFolder() {
        scrapService?.getScrapFolder()?.enqueue(object: Callback<ScrapFolderDataResponse> {
            override fun onResponse(
                call: Call<ScrapFolderDataResponse>,
                response: Response<ScrapFolderDataResponse>
            ) {
                if (response.code() == 200) {
                    val scrapFolderResponse = response.body()
                    if (scrapFolderResponse?.success == true) {
                        scrapFolderView.scrapFolderSuccessView(scrapFolderResponse.data)
                    } else {
                        scrapFolderView.scrapFolderFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<ScrapFolderDataResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-GET-SCRAP-FOLDER-FAILURE", t)
            }
        })
    }

    fun addScrapFolder(scrapFolderAddRequest: ScrapFolderAddRequest) {
        scrapService?.addScrapFolder(scrapFolderAddRequest)?.enqueue(object: Callback<ScrapFolderAddResponse> {
            override fun onResponse(
                call: Call<ScrapFolderAddResponse>,
                response: Response<ScrapFolderAddResponse>
            ) {
                if (response.code() == 200) {
                    val scrapFolderAddResponse = response.body()
                    if (scrapFolderAddResponse?.success == true) {
                        scrapFolderAddView.scrapFolderAddSuccessView(scrapFolderAddResponse.data)
                    } else {
                        scrapFolderAddView.scrapFolderAddFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<ScrapFolderAddResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-ADD-SCRAP-FOLDER-FAILURE", t)
            }
        })
    }

    fun deleteScrapFolder(scrapFolderId: Int) {
        scrapService?.deleteScrapFolder(scrapFolderId)?.enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.code() == 200) {
                    scrapFolderDeleteView.scrapFolderDeleteSuccessView()
                } else {
                    scrapFolderDeleteView.scrapFolderDeleteFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-DELETE-SCRAP-FOLDER-FAILURE", t)
            }
        })
    }

    fun getScrapCourse(scrapFolderId: Int) {
        scrapService?.getScrapCourse(scrapFolderId)?.enqueue(object: Callback<ScrapCourseResponse> {
            override fun onResponse(
                call: Call<ScrapCourseResponse>,
                response: Response<ScrapCourseResponse>
            ) {
                Log.d("API-TEST", "response.body = ${response.body()}")
                if (response.code() == 200) {
                    val scrapCourseResponse = response.body()
                    if (scrapCourseResponse?.success == true) {
                        scrapCourseView.scrapCourseSuccessView(scrapCourseResponse.data)
                    } else {
                        scrapCourseView.scrapCourseFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<ScrapCourseResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-GET-SCRAP-COURSE-FAILURE", t)
            }
        })
    }

    // MEMO: 테스트 필요!
    fun deleteScrapCourse(scrapFolderId: Int, courseId: ArrayList<Int>) {
        scrapService?.deleteScrapCourse(scrapFolderId, courseId)?.enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.d("API-TEST", "scrapFolderId = ${scrapFolderId}, courseId = $courseId")
                Log.d("API-TEST", "response.body = ${response.body()}")
                if (response.code() == 200) {
                    scrapCourseDeleteView.scrapCourseDeleteSuccessView()
                } else {
                    scrapCourseDeleteView.scrapCourseDeleteFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-DELETE-SCRAP-COURSE-FAILURE", t)
            }
        })
    }
}