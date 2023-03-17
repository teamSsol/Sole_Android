package cmc.sole.android.Home.Search

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.ActivitySearchBinding

class SearchActivity: BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {

    private lateinit var searchWordRVAdapter: SearchWordRVAdapter
    private var searchWordList = ArrayList<SearchData>()
    private lateinit var searchResultRVAdapter: HomeDefaultCourseRVAdapter
    private var searchResultList = ArrayList<DefaultCourse>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        searchWordRVAdapter = SearchWordRVAdapter(searchWordList)
        binding.searchRv.adapter = searchWordRVAdapter
        binding.searchRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 20))
        binding.searchRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // MEMO: DummyData
        searchWordList.add(SearchData("최근 검색어 1"))
        searchWordList.add(SearchData("최근 검색어 2"))
        searchWordList.add(SearchData("최근 검색어 3"))
        searchWordList.add(SearchData("최근 검색어 4"))
        searchWordList.add(SearchData("최근 검색어 5"))
    }

    private fun initSearchResult() {
        searchResultRVAdapter = HomeDefaultCourseRVAdapter(searchResultList)
        binding.searchResultRv.adapter = searchResultRVAdapter
        binding.searchResultRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        binding.searchResultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        searchResultRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int) {
                startActivity(Intent(this@SearchActivity, CourseDetailActivity::class.java))
            }
        })

        // MEMO: DummyData
//        searchResultList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
    }

    private fun initClickListener() {
        binding.searchBackIv.setOnClickListener {
            finish()
        }

        binding.searchTextEt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    binding.searchDefaultLayout.visibility = View.GONE
                    binding.searchResultRv.visibility = View.VISIBLE

                    initSearchResult()
                }
            }
            true
        })
    }
}