package cmc.sole.android.Scrap.Retrofit

import android.util.Log
import cmc.sole.android.DefaultResponse
import cmc.sole.android.Home.HomePopularResponse
import cmc.sole.android.Home.Retrofit.HomeRetrofitInterface
import com.example.geeksasaeng.Utils.NetworkModule
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScrapService {
    private val scrapService = NetworkModule.getInstance()?.create(ScrapRetrofitInterfaces::class.java)

    private lateinit var scrapFolderView: ScrapFolderView
    private lateinit var scrapDefaultFolderView: ScrapDefaultFolderView
    private lateinit var scrapDefaultFolderDeleteView: ScrapDefaultFolderCourseDeleteView
    private lateinit var scrapFolderAddView: ScrapFolderAddView
    private lateinit var scrapFolderDeleteView: ScrapFolderDeleteView
    private lateinit var scrapFolderNameUpdateView: ScrapFolderNameUpdateView
    private lateinit var scrapCourseView: ScrapCourseView
    private lateinit var scrapCourseDeleteView: ScrapCourseDeleteView
    private lateinit var scrapCourseMoveView: ScrapCourseMoveView

    fun setScrapFolderView(scrapFolderView: ScrapFolderView) {
        this.scrapFolderView = scrapFolderView
    }
    fun setScrapDefaultFolderView(scrapDefaultFolderView: ScrapDefaultFolderView) {
        this.scrapDefaultFolderView = scrapDefaultFolderView
    }
    fun setScrapDefaultFolderDeleteView(scrapDefaultFolderDeleteView: ScrapDefaultFolderCourseDeleteView) {
        this.scrapDefaultFolderDeleteView = scrapDefaultFolderDeleteView
    }
    fun setScrapFolderAddView(scrapFolderAddView: ScrapFolderAddView) {
        this.scrapFolderAddView = scrapFolderAddView
    }
    fun setScrapFolderDeleteView(scrapFolderDeleteView: ScrapFolderDeleteView) {
        this.scrapFolderDeleteView = scrapFolderDeleteView
    }
    fun setScrapFolderNameUpdateView(scrapFolderNameUpdateView: ScrapFolderNameUpdateView) {
        this.scrapFolderNameUpdateView = scrapFolderNameUpdateView
    }
    fun setScrapCourseView(scrapCourseView: ScrapCourseView) {
        this.scrapCourseView = scrapCourseView
    }
    fun setScrapCourseDeleteView(scrapCourseDeleteView: ScrapCourseDeleteView) {
        this.scrapCourseDeleteView = scrapCourseDeleteView
    }
    fun setScrapCourseMoveView(scrapCourseMoveView: ScrapCourseMoveView) {
        this.scrapCourseMoveView = scrapCourseMoveView
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

    fun getDefaultFolder() {
        scrapService?.getDefaultScrapFolder()?.enqueue(object: Callback<ScrapDefaultFolderDataResponse> {
            override fun onResponse(
                call: Call<ScrapDefaultFolderDataResponse>,
                response: Response<ScrapDefaultFolderDataResponse>
            ) {
                if (response.code() == 200) {
                    val scrapDefaultFolderResponse = response.body()
                    if (scrapDefaultFolderResponse?.success == true) {
                        scrapDefaultFolderView.scrapDefaultFolderSuccessView(scrapDefaultFolderResponse.data)
                    } else {
                        scrapDefaultFolderView.scrapDefaultFolderFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<ScrapDefaultFolderDataResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-GET-SCRAP-DEFAULT-FOLDER-FAILURE", t)
            }
        })
    }

    fun deleteScrapDefaultFolderCourse(courseIds: ArrayList<Int>) {
        scrapService?.deleteDefaultFolder(courseIds)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    scrapDefaultFolderDeleteView.scrapDefaultFolderCourseDeleteSuccessView()
                } else {
                    scrapDefaultFolderDeleteView.scrapDefaultFolderCourseDeleteFailureView()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-DELETE-SCRAP-DEFAULT-FOLDER-FAILURE", t)
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

    fun updateScrapFolderName(scrapFolderId: Int, scrapFolderName: ScrapFolderNameUpdateRequest) {
        scrapService?.updateScrapFolderName(scrapFolderId, scrapFolderName)?.enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.code() == 200) {
                    scrapFolderNameUpdateView.scrapFolderNameUpdateSuccessView()
                } else {
                    scrapFolderNameUpdateView.scrapFolderNameUpdateFailureView()
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-SCRAP-FOLDER-NAME-UPDATE-FAILURE", t)
            }
        })
    }

    fun getScrapCourse(scrapFolderId: Int) {
        scrapService?.getScrapCourse(scrapFolderId)?.enqueue(object: Callback<ScrapCourseResponse> {
            override fun onResponse(
                call: Call<ScrapCourseResponse>,
                response: Response<ScrapCourseResponse>
            ) {
                // Log.d("API-TEST", "response.body = ${response.body()}")
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
                // Log.d("API-TEST", "scrapFolderId = ${scrapFolderId}, courseId = $courseId")
                // Log.d("API-TEST", "response = $response")
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

    fun moveScrapCourse(scrapFolderId: Int, courseIds: ScrapFolderCourseMoveRequest) {
        scrapService?.moveDefaultScrapFolder(scrapFolderId, courseIds)?.enqueue(object: Callback<ScrapFolderCourseMoveResponse> {
            override fun onResponse(
                call: Call<ScrapFolderCourseMoveResponse>,
                response: Response<ScrapFolderCourseMoveResponse>
            ) {
                // Log.d("API-TEST", "response = $response")
                // Log.d("API-TEST", "response.body = ${response.body()}")
                if (response.code() == 200) {
                    val scrapCourseMoveResponse = response.body()
                    if (scrapCourseMoveResponse?.success == true) {
                        scrapCourseMoveView.scrapCourseMoveSuccessView(scrapCourseMoveResponse.data)
                    } else {
                        scrapCourseMoveView.scrapCourseMoveFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<ScrapFolderCourseMoveResponse>, t: Throwable) {
                Log.e("SCRAP-SERVICE", "SCRAP-SERVICE-MOVE-SCRAP-COURSE-FAILURE", t)
            }
        })
    }
}