package cmc.sole.android.MyCourse.Write.Search

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.R
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteSearchBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MyCourseWriteSearchBottomFragment: BottomSheetDialogFragment() {
    private lateinit var binding: BottomFragmentMyCourseWriteSearchBinding

    lateinit var sb: StringBuilder
    private val display = 5 // 검색결과갯수. 최대100개

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FIX: 태그 여러 개로 다시 나오는 오류 수정 필요!!
        binding = BottomFragmentMyCourseWriteSearchBinding.inflate(inflater, container, false)
        searchKeyword("은행")
//        Thread {
//            try {
//                val text: String = URLEncoder.encode("아트메가128", "utf-8")
//                val apiURL = "https://openapi.naver.com/v1/search/blog.json?query=$text&display=$display&"
//                val url = URL(apiURL)
//                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
//                con.setRequestMethod("GET")
//                con.setRequestProperty("X-Naver-Client-Id", resources.getString(R.string.naver_client_id))
//                con.setRequestProperty("X-Naver-Client-Secret", resources.getString(R.string.naver_client_secret))
//                val responseCode: Int = con.getResponseCode()
//                val br: BufferedReader
//                if (responseCode == 200) {
//                    br = BufferedReader(InputStreamReader(con.getInputStream()))
//                } else {
//                    br = BufferedReader(InputStreamReader(con.getErrorStream()))
//                }
//                sb = StringBuilder()
//                var line: String
//                while (br.readLine().also { line = it } != null) {
//                    sb.append(
//                        """
//                $line
//
//                """.trimIndent()
//                    )
//                }
//                br.close()
//                con.disconnect()
//                Log.d("WRITE-TEST", "${sb.toString()}")
//
//            } catch (e: Exception) {
//                Log.d("WRITE-TEST", e.toString())
//            }
//        }.start()


        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 55 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl("https://openapi.naver.com/v1/").addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(NaverSearchAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.searchKeyword(resources.getString(R.string.naver_client_id2),
            resources.getString(R.string.naver_client_secret2),
            "local.json", keyword, 5)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<SearchNaverData> {
            override fun onResponse(
                call: Call<SearchNaverData>,
                response: Response<SearchNaverData>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("API-TEST", "Raw: ${response.raw()}")
                Log.d("API-TEST", "Body: ${response.body()?.items}")
            }
            override fun onFailure(call: Call<SearchNaverData>, t: Throwable) {
                // 통신 실패
                Log.w("API-TEST", "통신 실패: ${t.message}")
            }
        })
    }
}