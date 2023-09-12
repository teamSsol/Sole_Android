package cmc.sole.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Home.HomeCategoriesResult
import cmc.sole.android.Home.Retrofit.HomeCategoriesUpdateView
import cmc.sole.android.Home.Retrofit.HomeGetCategoriesView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.MyCourseTagButtonRVAdapter
import cmc.sole.android.MyCourse.Retrofit.MyCourseAddPlace
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryRequest
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.Write.MyCourseWritePlaceRVAdapter

import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding
import cmc.sole.android.databinding.ActivityStartCourseTagBinding
import com.google.android.flexbox.FlexboxLayoutManager

class StartCourseTagActivity: AppCompatActivity(),
    HomeCategoriesUpdateView, HomeGetCategoriesView {

    lateinit var binding: ActivityStartCourseTagBinding

    private lateinit var myCourseTagPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagTransRVAdapter: MyCourseTagButtonRVAdapter
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var tagFlag = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

    private lateinit var homeService: HomeService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartCourseTagBinding.inflate(layoutInflater)

        var flag = intent.getStringExtra("flag")
        if (flag == "signupFinish") {
            binding.startCourseTagSkipTv.visibility = View.VISIBLE
        } else {
            binding.startCourseTagSkipTv.visibility = View.INVISIBLE
        }

        initService()
        initAdapter()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setHomeCategoriesUpdateView(this)
        homeService.setHomeGetCategoriesView(this)
        homeService.getCategories()
    }

    private fun initAdapter() {
        myCourseTagPlaceRVAdapter = MyCourseTagButtonRVAdapter(placeTagList)
        binding.myCoursePlaceTagRv.adapter = myCourseTagPlaceRVAdapter
        binding.myCoursePlaceTagRv.layoutManager = FlexboxLayoutManager(this)
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagPlaceRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position] = data.isChecked
            }
        })

        placeTagList.add(TagButton(1, resources.getString(R.string.place_tag1), tagFlag[0]))
        placeTagList.add(TagButton(2, resources.getString(R.string.place_tag2), tagFlag[1]))
        placeTagList.add(TagButton(3, resources.getString(R.string.place_tag3), tagFlag[2]))
        placeTagList.add(TagButton(4, resources.getString(R.string.place_tag4), tagFlag[3]))
        placeTagList.add(TagButton(5, resources.getString(R.string.place_tag5), tagFlag[4]))
        placeTagList.add(TagButton(6, resources.getString(R.string.place_tag6), tagFlag[5]))
        placeTagList.add(TagButton(7, resources.getString(R.string.place_tag7), tagFlag[6]))
        placeTagList.add(TagButton(8, resources.getString(R.string.place_tag8), tagFlag[7]))
        placeTagList.add(TagButton(9, resources.getString(R.string.place_tag9), tagFlag[8]))
        placeTagList.add(TagButton(null, "", false))

        myCourseTagWithRVAdapter = MyCourseTagButtonRVAdapter(withTagList)
        binding.myCourseWithTagRv.adapter = myCourseTagWithRVAdapter
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(this)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagWithRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position] = data.isChecked
            }
        })

        withTagList.add(TagButton(10, resources.getString(R.string.with_tag10), tagFlag[9]))
        withTagList.add(TagButton(11, resources.getString(R.string.with_tag11), tagFlag[10]))
        withTagList.add(TagButton(12, resources.getString(R.string.with_tag12), tagFlag[11]))
        withTagList.add(TagButton(13, resources.getString(R.string.with_tag13), tagFlag[12]))
        withTagList.add(TagButton(14, resources.getString(R.string.with_tag14), tagFlag[13]))
        withTagList.add(TagButton(null, "", false))

        myCourseTagTransRVAdapter = MyCourseTagButtonRVAdapter(transTagList)
        binding.myCourseTransportTagRv.adapter = myCourseTagTransRVAdapter
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(this)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagTransRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position] = data.isChecked
            }
        })

        transTagList.add(TagButton(15, resources.getString(R.string.trans_tag15), tagFlag[14]))
        transTagList.add(TagButton(16, resources.getString(R.string.trans_tag16), tagFlag[15]))
        transTagList.add(TagButton(17, resources.getString(R.string.trans_tag17), tagFlag[16]))
        transTagList.add(TagButton(18, resources.getString(R.string.trans_tag18), tagFlag[17]))
        transTagList.add(TagButton(null, "", false))
    }

    private fun initClickListener() {
        binding.startCourseTagSkipTv.setOnClickListener {
            changeActivity(MainActivity::class.java)
            finish()
        }

        binding.myCourseTagOkBtn.setOnClickListener {
            var returnList = mutableListOf<TagButton>()
            for (i in 0..8) {
                returnList.add(placeTagList[i])
            }
            for (i in 0..4) {
                returnList.add(withTagList[i])
            }
            for (i in 0..3) {
                returnList.add(transTagList[i])
            }

            // UPDATE: 이 부분 타입 수정하기 (returnTag ReturnType MutableSet<String> -> MutableSet<Categories>
            var myCourseHistoryRequest = TagSettingRequest(returnTag("place"), returnTag("trans"), returnTag("with"))
            homeService.updateCategories(myCourseHistoryRequest)
        }
    }

    private fun returnTag(option: String): MutableSet<Categories> {
        var resultTagSet = mutableSetOf<Categories>()
        when(option) {
            "place" -> {
                for (i in 0..8) {
                    if (myCourseTagPlaceRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(Translator.returnTagEngStr(i + 1))
                }
            }
            "with" -> {
                for (i in 0..4) {
                    if (myCourseTagWithRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(Translator.returnTagEngStr(i + 10))
                }
            }
            else -> {
                for (i in 0..3) {
                    if (myCourseTagTransRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(Translator.returnTagEngStrStep(i + 15))
                }
            }
        }

        Log.d("API-TEST", "resultTagSet = $resultTagSet")

        return resultTagSet
    }

    override fun homeCategoriesUpdateSuccessView() {
        finish()
    }

    override fun homeCategoriesUpdateFailureView() {

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun homeGetCategoriesUpdateSuccessView(data: HomeCategoriesResult) {
        for (i in 0 until data.placeCategories.size) {
            var index = Translator.tagEngPosition(this, data.placeCategories.elementAt(i)!!.name)
            tagFlag[index - 1] = true
            myCourseTagPlaceRVAdapter.getItem(index - 1).isChecked = true
            myCourseTagPlaceRVAdapter.notifyItemChanged(index - 1)
        }
        for (i in 0 until data.withCategories.size) {
            var index = Translator.tagEngPosition(this, data.withCategories.elementAt(i)!!.name)
            tagFlag[index - 10] = true
            myCourseTagWithRVAdapter.getItem(index - 10).isChecked = true
            myCourseTagWithRVAdapter.notifyItemChanged(index - 10)
        }
        for (i in 0 until data.transCategories.size) {
            var index = Translator.tagEngPosition(this, data.transCategories.elementAt(i)!!.name)
            tagFlag[index - 15] = true

            if (data.transCategories.elementAt(i)!!.name == "CAR") {
                tagFlag[17] = true
                myCourseTagTransRVAdapter.getItem(3).isChecked = true
                myCourseTagTransRVAdapter.notifyItemChanged(3)
            }
            else if (data.transCategories.elementAt(i)!!.name == "BIKE") {
                tagFlag[16] = true
                myCourseTagTransRVAdapter.getItem(2).isChecked = true
                myCourseTagTransRVAdapter.notifyItemChanged(2)
            }
            else if (data.transCategories.elementAt(i)!!.name == "PUBLIC_TRANSPORTATION") {
                tagFlag[15] = true
                myCourseTagTransRVAdapter.getItem(1).isChecked = true
                myCourseTagTransRVAdapter.notifyItemChanged(1)

            } else {
                tagFlag[14] = true
                myCourseTagTransRVAdapter.getItem(0).isChecked = true
                myCourseTagTransRVAdapter.notifyItemChanged(0)
            }
        }
    }

    override fun homeGetCategoriesUpdateFailureView() {

    }

    private fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}