package cmc.sole.android.MyCourse.Write.Search

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.MyCourse.Write.MyCourseWriteViewModel
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
    private lateinit var searchResultRVAdapter: MyCourseSearchResultRVAdapter
    private var searchResultList = ArrayList<SearchResultData>()

    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    lateinit var sb: StringBuilder
    private val display = 5 // 검색결과갯수. 최대100개

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentMyCourseWriteSearchBinding.inflate(inflater, container, false)
        initSearchListener()
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        searchResultRVAdapter = MyCourseSearchResultRVAdapter(searchResultList)
        binding.myCourseWriteSearchResultRv.adapter = searchResultRVAdapter
        binding.myCourseWriteSearchResultRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchResultRVAdapter.setOnItemClickListener(object: MyCourseSearchResultRVAdapter.OnItemClickListener {
            override fun onItemClick(data: SearchResultData, position: Int) {
                writeVM.setPlaceInfo(data)
            }
        })
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

    private fun initSearchListener() {
        binding.myCourseWriteSearchBottomTextEt.setOnEditorActionListener { _, actionId, _ ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchKeyword(binding.myCourseWriteSearchBottomTextEt.text.toString())
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.myCourseWriteSearchBottomTextEt.windowToken, 0)
                handled = true
            }
            handled
        }
    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/").addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(NaverSearchAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.searchKeyword(resources.getString(R.string.naver_client_id2),
            resources.getString(R.string.naver_client_secret2), "local.json", keyword, 3)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<SearchNaverData> {
            override fun onResponse(
                call: Call<SearchNaverData>,
                response: Response<SearchNaverData>
            ) {
                if (response.body()?.items != null) {
                    searchResultRVAdapter.addAllItems(response.body()?.items!! as ArrayList<SearchResultData>)
                }
            }
            override fun onFailure(call: Call<SearchNaverData>, t: Throwable) {
                Log.w("API-TEST", "통신 실패: ${t.message}")
            }
        })
    }
}