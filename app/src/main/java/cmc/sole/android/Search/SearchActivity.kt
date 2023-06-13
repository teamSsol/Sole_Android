package cmc.sole.android.Search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.Retrofit.HomeDefaultCourseView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Search.RoomDB.SearchWord
import cmc.sole.android.Search.RoomDB.SearchWordDao
import cmc.sole.android.Search.RoomDB.SearchWordDatabase
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchActivity: AppCompatActivity(),
    HomeDefaultCourseView {

    lateinit var binding: ActivitySearchBinding

    private var recentWordDB: SearchWordDatabase? = null
    private var recentDao: SearchWordDao? = null
    private lateinit var searchWordRVAdapter: SearchWordRVAdapter
    private var searchWordList = ArrayList<SearchWord>()
    private lateinit var searchResultRVAdapter: HomeDefaultCourseRVAdapter
    private var searchResultList = ArrayList<DefaultCourse>()
    private lateinit var searchService: HomeService
    var courseId: Int? = null
    var lastCourseId: Int? = null
    private var searchWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        recentWordDB = SearchWordDatabase.getDBInstance(this)
        recentDao = recentWordDB?.searchWordDao()

        initSetRecentWord()
        initService()
        initAdapter()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initSetRecentWord() {
        CoroutineScope(Dispatchers.IO).launch {
            var allRecentWord = recentDao?.getAllWord()?.reversed()
            if (allRecentWord!!.isNotEmpty()) {
                searchWordRVAdapter.addAllItem(allRecentWord.toMutableList())
            }

            withContext(Dispatchers.Main) {
                if (allRecentWord.isNotEmpty()) {
                    binding.searchAllDeleteTv.visibility = View.VISIBLE
                } else {
                    binding.searchAllDeleteTv.visibility = View.GONE
                }
            }
        }
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
        searchWordRVAdapter.setOnClickListener(object: SearchWordRVAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(data: SearchWord, position: Int) {
                if (searchWordRVAdapter.returnMode() == 0) {
                    // MEMO: X 누를 때
                    CoroutineScope(Dispatchers.IO).launch {
                        recentDao?.deleteWord(data.searchWord)
                        withContext(Dispatchers.Main) {
                            searchWordRVAdapter.removeItem(position)
                            searchWordRVAdapter.notifyDataSetChanged()

                            if (searchWordRVAdapter.returnListSize() == 0) {
                                binding.searchDefaultLayout.visibility = View.GONE
                            }
                        }
                    }
                } else if (searchWordRVAdapter.returnMode() == 1) {
                    // MEMO: 단어 누를 때
                    binding.searchDefaultLayout.visibility = View.GONE
                    binding.searchResultRv.visibility = View.VISIBLE
                    binding.searchTextEt.setText(data.searchWord)
                    searchService.getHomeDefaultCourse(courseId, data.searchWord)
                }
            }
        })

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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.searchTextEt.isCursorVisible) {
            binding.searchTextEt.isCursorVisible = false
        } else {
            if (binding.searchResultRv.visibility == View.GONE) {
                finish()
            } else if (binding.searchResultRv.visibility == View.VISIBLE) {
                CoroutineScope(Dispatchers.IO).launch {
                    var allRecentWord = recentDao?.getAllWord()?.reversed()
                    searchWordRVAdapter.removeAllItem()
                    searchWordRVAdapter.addAllItem(allRecentWord!!.toMutableList())
                }
                binding.searchDefaultLayout.visibility = View.VISIBLE
                binding.searchResultRv.visibility = View.GONE
                binding.searchTextEt.setText("")

                searchResultRVAdapter.removeAllItems()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initClickListener() {
        binding.searchBackIv.setOnClickListener {
            if (binding.searchResultRv.visibility == View.GONE) {
                finish()
            } else if (binding.searchResultRv.visibility == View.VISIBLE) {
                CoroutineScope(Dispatchers.IO).launch {
                    var allRecentWord = recentDao?.getAllWord()?.reversed()
                    searchWordRVAdapter.removeAllItem()
                    searchWordRVAdapter.addAllItem(allRecentWord!!.toMutableList())
                }
                binding.searchDefaultLayout.visibility = View.VISIBLE
                binding.searchResultRv.visibility = View.GONE
                binding.searchTextEt.setText("")

                searchResultRVAdapter.removeAllItems()
            }
        }

        binding.searchAllDeleteTv.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                recentDao?.deleteAllRecentWord()
                withContext(Dispatchers.Main) {
                    searchWordRVAdapter.removeAllItem()
                    searchWordRVAdapter.notifyDataSetChanged()
                    binding.searchDefaultLayout.visibility = View.GONE
                }
            }
        }

        binding.searchTextEt.setOnClickListener {
            binding.searchTextEt.isCursorVisible = true
            binding.searchTextEt.setSelection(binding.searchTextEt.length())
        }

        binding.searchTextEt.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchTextEt.windowToken, 0)

                binding.searchDefaultLayout.visibility = View.GONE
                binding.searchResultRv.visibility = View.VISIBLE

                searchWord = binding.searchTextEt.text.toString()
                searchResultRVAdapter.removeAllItems()

                searchService.getHomeDefaultCourse(courseId, searchWord)

                CoroutineScope(Dispatchers.IO).launch {
                    val searchWord = binding.searchTextEt.text.toString()
                    recentDao?.deleteWord(searchWord)
                    recentDao?.insert(SearchWord(searchWord))
                }

                true
            }

            false
        }

        binding.courseMoreCv.setOnClickListener {
            searchService.getHomeDefaultCourse(lastCourseId, searchWord)
        }
    }

    override fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {
        binding.searchRv.visibility = View.VISIBLE
        binding.searchDefaultLayout.visibility = View.GONE

        if (homeDefaultResponse.data.size != 0) {
            // MEMO: 마지막 페이지가 아니라면 더 보기 버튼 보여주기
            var lastCourse = homeDefaultResponse.data[homeDefaultResponse.data.size - 1]
            if (!lastCourse.finalPage) {
                lastCourseId = lastCourse.courseId
                binding.courseMoreCv.visibility = View.VISIBLE
            } else binding.courseMoreCv.visibility = View.GONE
        }

        searchResultRVAdapter.addAllItems(homeDefaultResponse.data)
    }

    override fun homeDefaultCourseFailureView() {

    }
}