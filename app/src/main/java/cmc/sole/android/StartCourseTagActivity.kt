package cmc.sole.android

import android.util.Log
import cmc.sole.android.Home.HomeCategoriesResult
import cmc.sole.android.Home.Retrofit.HomeCategoriesUpdateView
import cmc.sole.android.Home.Retrofit.HomeGetCategoriesView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.MyCourseTagButtonRVAdapter
import cmc.sole.android.MyCourse.Retrofit.MyCourseAddPlace
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryRequest
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.MyCourse.Write.MyCourseWritePlaceRVAdapter
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ActivityStartCourseTagBinding
import com.google.android.flexbox.FlexboxLayoutManager

class StartCourseTagActivity: BaseActivity<ActivityStartCourseTagBinding>(ActivityStartCourseTagBinding::inflate),
    HomeCategoriesUpdateView, HomeGetCategoriesView {

    private lateinit var myCourseTagBottomPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomTransRVAdapter: MyCourseTagButtonRVAdapter
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var checkTagList: MutableList<TagButton> = mutableListOf()
    private var tagFlag = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

    private lateinit var homeService: HomeService

    override fun initAfterBinding() {
        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setHomeCategoriesUpdateView(this)
        homeService.setHomeGetCategoriesView(this)
        homeService.getCategories()
    }

    private fun initAdapter() {
        myCourseTagBottomPlaceRVAdapter = MyCourseTagButtonRVAdapter(placeTagList)
        binding.myCoursePlaceTagRv.adapter = myCourseTagBottomPlaceRVAdapter
        binding.myCoursePlaceTagRv.layoutManager = FlexboxLayoutManager(this)
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomPlaceRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
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

        myCourseTagBottomWithRVAdapter = MyCourseTagButtonRVAdapter(withTagList)
        binding.myCourseWithTagRv.adapter = myCourseTagBottomWithRVAdapter
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(this)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomWithRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
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

        myCourseTagBottomTransRVAdapter = MyCourseTagButtonRVAdapter(transTagList)
        binding.myCourseTransportTagRv.adapter = myCourseTagBottomTransRVAdapter
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(this)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomTransRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
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
            Log.d("API-TEST", "returnList = $returnList")

            var myCourseHistoryRequest = MyCourseHistoryRequest(returnTag("place"), returnTag("trans"), returnTag("with"))
            homeService.updateCategories(myCourseHistoryRequest)
        }
    }

    private fun returnTag(option: String): MutableSet<String> {
        var resultTagSet = mutableSetOf<String>()
        when(option) {
            "place" -> {
                for (i in 0..8) {
                    if (myCourseTagBottomPlaceRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(Translator.tagKorToEng(this, myCourseTagBottomPlaceRVAdapter.getItem(i).title))
                }
            }
            "with" -> {
                for (i in 0..4) {
                    if (myCourseTagBottomWithRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(Translator.tagKorToEng(this, myCourseTagBottomWithRVAdapter.getItem(i).title))
                }
            }
            else -> {
                for (i in 0..3) {
                    if (myCourseTagBottomTransRVAdapter.getItem(i).isChecked)
                        resultTagSet.add(Translator.tagKorToEng(this, myCourseTagBottomTransRVAdapter.getItem(i).title))
                }
            }
        }

        return resultTagSet
    }

    override fun homeCategoriesUpdateSuccessView() {
        changeActivity(MainActivity::class.java)
        finish()
    }

    override fun homeCategoriesUpdateFailureView() {
        showToast("카테고리 저장 실패")
    }

    override fun homeGetCategoriesUpdateSuccessView(data: HomeCategoriesResult) {
        // TODO: 선택했던 태그 표시
//        for (i in 0 until data.placeCategories.size) {
//            var index = Translator.tagEngPosition(this, data.placeCategories.elementAt(i)!!.name)
//            tagFlag[index - 1] = true
//            Log.d("API-TEST", "index 1 = $index")
//            myCourseTagBottomPlaceRVAdapter.getItem(index - 1).isChecked = true
//            myCourseTagBottomPlaceRVAdapter.notifyDataSetChanged()
//        }
//        for (i in 0 until data.withCategories.size) {
//            var index = Translator.tagEngPosition(this, data.withCategories.elementAt(i)!!.name)
//            tagFlag[index - 1] = true
//            Log.d("API-TEST", "index 2 = $index")
//            myCourseTagBottomWithRVAdapter.getItem(index - 10).isChecked = true
//            myCourseTagBottomWithRVAdapter.notifyDataSetChanged()
//        }
//        for (i in 0 until data.transCategories.size) {
//            var index = Translator.tagEngPosition(this, data.transCategories.elementAt(i)!!.name)
//
//            if (data.transCategories.elementAt(i)!!.name == "CAR") tagFlag[17] = true
//            else if (data.transCategories.elementAt(i)!!.name == "BIKE") tagFlag[16]
//            else if (data.transCategories.elementAt(i)!!.name == "PUBLIC_TRANSPORTATION") tagFlag[15]
//            Log.d("API-TEST", "index 3 = $index")
//            myCourseTagBottomTransRVAdapter.getItem(index - 15).isChecked = true
//            myCourseTagBottomTransRVAdapter.notifyDataSetChanged()
//        }
    }

    override fun homeGetCategoriesUpdateFailureView() {
        showToast("카테고리 불러오기 실패")
    }
}