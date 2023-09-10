package cmc.sole.android.Search

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.Home.HomeDefaultResponse
import cmc.sole.android.Home.Retrofit.HomeDefaultCourseView
import cmc.sole.android.Home.Retrofit.HomeFilterCourseView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.MyCourseOptionBottomFragment
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.R
import cmc.sole.android.Search.RoomDB.SearchWord
import cmc.sole.android.Search.RoomDB.SearchWordDao
import cmc.sole.android.Search.RoomDB.SearchWordDatabase
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Region
import cmc.sole.android.Utils.Translator
import cmc.sole.android.Utils.returnRegionCode
import cmc.sole.android.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException


class SearchActivity: AppCompatActivity(), HomeDefaultCourseView, HomeFilterCourseView {

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

    // MEMO: 필터 적용
    var tagFlagList = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    var regionFlagList = arrayListOf<String>()
    var placeCategories: HashSet<Categories> = hashSetOf()
    var transCategories: HashSet<Categories> = hashSetOf()
    var withCategories: HashSet<Categories> = hashSetOf()
    var regions: HashSet<Region> = hashSetOf()
    private var prePlaceCategories: HashSet<Categories> = hashSetOf()
    private var preTransCategories: HashSet<Categories> = hashSetOf()
    private var preWithCategories: HashSet<Categories> = hashSetOf()
    private var preRegions: HashSet<Categories> = hashSetOf()

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
        searchService.setHomeFilterCourseView(this)
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
                    checkFilterAndApplyAPI()
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
                binding.searchFilterCv.visibility = View.GONE
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
                binding.searchFilterCv.visibility = View.GONE
                binding.courseMoreCv.visibility = View.GONE
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
            checkFilterAndApplyAPI()
        }


        binding.searchFilterCv.setOnClickListener {
            val myCourseOptionBottomFragment = MyCourseOptionBottomFragment()
            var bundle = Bundle()
            bundle.putString("viewFlag", "searchActivity")
            bundle.putBooleanArray("tagFlag", tagFlagList)
            bundle.putStringArrayList("regionFlag", regionFlagList)
            myCourseOptionBottomFragment.arguments = bundle
            myCourseOptionBottomFragment.show(supportFragmentManager!!, "CourseDetailOptionBottom")
            myCourseOptionBottomFragment.setOnFinishListener(object: MyCourseOptionBottomFragment.OnTagFragmentFinishListener {
                override fun finish(returnTagList: List<TagButton>, returnRegionList: ArrayList<String>) {
                    Log.d("API-TEST", "returnTagList = $returnTagList / returnRegionList = $returnRegionList")

                    // MEMO: 태그
                    for (i in 0..17) {
                        tagFlagList[i] = returnTagList[i].isChecked
                    }

                    var tagArrayList = arrayListOf<String>()
                    for (i in 0..17) {
                        if (tagFlagList[i]) tagArrayList.add(returnTagList[i].title)
                    }

                    tagArrayList.add("")
                    // tagRVAdapter.addAllItems(tagArrayList)

                    // MEMO: 지역 필터
                    regionFlagList = returnRegionList
                    var regionList = hashSetOf<Region>()
                    for (i in 0 until returnRegionList.size) {
                        regionList.add(returnRegionCode(returnRegionList[i]))
                    }

                    regions = regionList
                    placeCategories = returnCategories("PLACE")
                    withCategories = returnCategories("WITH")
                    transCategories = returnCategories("TRANS")

                    Log.d("API-TEST", "region = $regions / placeCategories = $placeCategories / withCategories = $withCategories / transCategories = $transCategories")
                    Log.d("API-TEST", "region = ${regions.toString()} / placeCategories = ${placeCategories.toString()} / withCategories = ${withCategories.toString()} / transCategories = ${transCategories.toString()}")

                    checkFilterAndApplyAPI()

                    var map = hashSetOf(Categories.ACTIVITY, Categories.CAFE)
                    Log.d("API-TEST", "map = ${map}")
                }
            })
        }
    }

    // private fun returnCategories(option: String): MutableSet<Categories> {
    private fun returnCategories(option: String): HashSet<Categories> {
        // var returnCategoriesArray = mutableSetOf<Categories>()
        var returnCategoriesArray = hashSetOf<Categories>()

        when(option) {
            "PLACE" -> {
                for (i in 0..8) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1))
                    }
                }
            } "WITH" -> {
            for (i in 9..13) {
                if (tagFlagList[i]) {
                    returnCategoriesArray.add(Translator.returnTagEngStr(i + 1))
                }
            }
        } else -> {
            for (i in 14..17) {
                if (tagFlagList[i]) {
                    returnCategoriesArray.add(Translator.returnTagEngStr(i + 1))
                }
            }
        }
        }

        return returnCategoriesArray
    }

    private fun checkFilterAndApplyAPI() {
        if (placeCategories?.size == 0 && withCategories?.size == 0 && transCategories?.size == 0 && regions?.size == 0) {
            searchService.getHomeDefaultCourse(courseId, searchWord)
        } else {
            searchService.getHomeFilterCourse(courseId, searchWord, placeCategories, withCategories, transCategories, regions)
        }
    }

    override fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {
        Log.d("API-TEST", "DEFAULT")
        binding.searchRv.visibility = View.VISIBLE
        binding.searchDefaultLayout.visibility = View.GONE
        binding.searchFilterCv.visibility = View.VISIBLE
        binding.searchFilterCv.strokeColor = Color.parseColor("#D3D4D5")

        // MEMO: 이전에 필터를 설정했던 결과가 있었다면 이를 삭제하고 원래대로!!
        if (placeCategories?.size == 0 && withCategories?.size == 0 && transCategories?.size == 0 && regions?.size == 0) {
            searchResultRVAdapter.removeAllItems()
        }

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
        Log.d("API-TEST", "homeDefaultCourseFailureView")
    }

    override fun homeFilterCourseSuccessView(homeDefaultResponse: HomeDefaultResponse) {
        Log.d("API-TEST", "FILTER")
        Log.d("API-TEST", "homeDefaultResponse = ${homeDefaultResponse.data}")
        Log.d("API-TEST", "homeDefaultResponse = ${homeDefaultResponse.data[0].finalPage}")
        binding.searchRv.visibility = View.VISIBLE
        binding.searchDefaultLayout.visibility = View.GONE
        binding.searchFilterCv.visibility = View.VISIBLE
        binding.searchFilterCv.strokeColor = ContextCompat.getColor(binding.root.context, R.color.main)

        // MEMO: 이전에 필터를 설정했던 결과와 현재 필터가 다르다면 이를 삭제하고 원래대로!!
        if (prePlaceCategories != placeCategories || preWithCategories != withCategories || preTransCategories != transCategories || preRegions != regions) {
            searchResultRVAdapter.removeAllItems()
            prePlaceCategories = placeCategories
            preWithCategories = withCategories
            preTransCategories = transCategories
        }

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

    override fun homeFilterCourseFailureView() {
        Log.d("API-TEST", "homeFilterCourseFailureView")
    }
}