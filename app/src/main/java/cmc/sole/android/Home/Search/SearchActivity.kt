package cmc.sole.android.Home.Search

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.Retrofit.HomeDefaultCourseView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.ActivitySearchBinding

class SearchActivity: BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    HomeDefaultCourseView {

    private lateinit var searchWordRVAdapter: SearchWordRVAdapter
    private var searchWordList = ArrayList<SearchData>()
    private lateinit var searchResultRVAdapter: HomeDefaultCourseRVAdapter
    private var searchResultList = ArrayList<DefaultCourse>()
    private lateinit var searchService: HomeService
    var courseId = 5
    private lateinit var callback: OnBackPressedCallback

    override fun initAfterBinding() {
        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        searchService = HomeService()
        searchService.setHomeDefaultCourseView(this)
    }

    private fun initAdapter() {
        searchWordRVAdapter = SearchWordRVAdapter(searchWordList)
        binding.searchRv.adapter = searchWordRVAdapter
        binding.searchRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 20))
        binding.searchRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        searchResultRVAdapter = HomeDefaultCourseRVAdapter(searchResultList)
        binding.searchResultRv.adapter = searchResultRVAdapter
        binding.searchResultRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        binding.searchResultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        searchResultRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int, mode: String) {
                startActivity(Intent(this@SearchActivity, CourseDetailActivity::class.java))
            }
        })
    }

    private fun initClickListener() {
        binding.searchBackIv.setOnClickListener {
            finish()
        }

        binding.searchTextEt.setOnClickListener {
            binding.searchTextEt.setText(binding.searchTextEt.text.toString() + " dd")
            binding.searchTextEt.setSelection(binding.searchTextEt.length() - 1)
        }

        binding.searchTextEt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    binding.searchDefaultLayout.visibility = View.GONE
                    binding.searchResultRv.visibility = View.VISIBLE

                    binding.searchTextEt.isFocusable = false
                    binding.searchTextEt.isFocusableInTouchMode = false

                    var searchWord = binding.searchTextEt.text.toString()
                    showLog("API-TEST", "searchWord = $searchWord")
                    searchService.getHomeDefaultCourse(courseId, searchWord)
                }
            }
            true
        })

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
    }

    override fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {
        showLog("API-TEST", "searchResult = $homeDefaultResponse")
        searchResultRVAdapter.addAllItems(homeDefaultResponse.data)
    }

    override fun homeDefaultCourseFailureView() {
        showToast("검색 실패")
    }
}