package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.MyCourse.Retrofit.*
import cmc.sole.android.Write.MyCourseWriteViewModel
import cmc.sole.android.R
import cmc.sole.android.Utils.*
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteOptionNewBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cmc.sole.android.Utils.Region

class MyCourseOptionBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentMyCourseWriteOptionNewBinding
    private lateinit var myCourseTagBottomPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomTransRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseOptionLocationRVAdapter: MyCourseOptionLocationRVAdapter
    private lateinit var myCourseOptionRegionRVAdapter: MyCourseOptionLocationRegionRVAdapter
    private lateinit var myCourseOptionSelectLocationRVAdapter: MyCourseOptionLocationSelectRVAdapter
    private lateinit var myCourseService: MyCourseService
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var locationList = ArrayList<CityData>()
    private var regionList = ArrayList<RegionData>()
    private var selectRegionList = ArrayList<LocationData>()
    private lateinit var dialogFinishListener: OnTagFragmentFinishListener
    private var sendTag = listOf<TagButton>()
    private var tagFlag = booleanArrayOf()
    private var initialTagFlag = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    private var getRegionList = arrayListOf<String>()
    private var initialRegionList = mutableSetOf<Region>()
    private var returnRegionList = mutableSetOf<Region>()
    private var applyTag: Boolean = false
    // MEMO: 현재 선택하고 있는 지역을 알기 위함
    private var selectCityFlag = "서울"

    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.AppBottomDialogTheme
    }

    interface OnTagFragmentFinishListener {
        fun finish(tagSort: List<TagButton>, returnRegionList: MutableSet<Region>)
    }

    fun setOnFinishListener(listener: OnTagFragmentFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (applyTag) {
            returnTagList()
        } else cancel()
    }

    private fun cancel() {
        var returnList = mutableListOf<TagButton>()
        for (i in 0..8) {
            returnList.add(TagButton(i + 1, placeTagList[i].title, initialTagFlag[i]))
        }
        for (i in 0..4) {
            returnList.add(TagButton(i + 10, withTagList[i].title, initialTagFlag[9 + i]))
        }
        for (i in 0..3) {
            returnList.add(TagButton(i + 15, transTagList[i].title, initialTagFlag[14 + i]))
        }
        dialogFinishListener.finish(returnList, initialRegionList)
    }

    private fun returnTagList() {
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
        dialogFinishListener.finish(returnList, returnRegionList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomFragmentMyCourseWriteOptionNewBinding.inflate(inflater, container, false)

        initialTagFlag = requireArguments().getBooleanArray("tagFlag")!!
        tagFlag = initialTagFlag.copyOf()

        getRegionList = requireArguments().getStringArrayList("regionFlag")!!


        var a = "B01"
        var b = enumValueOf<Region>(a)

        initAdapter()
        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.myCourseTagOkBtn.setOnClickListener {
            // MEMO: 선택한 지역 리스트 API 연결
            var regionList = myCourseOptionSelectLocationRVAdapter.returnAllItems()
            returnRegionList = mutableSetOf<Region>()
            for (i in 0 until regionList.size) {
                returnRegionList.add(returnRegionCode(regionList[i].city + " " + regionList[i].region))
            }

            // MEMO: Tag 적용했는지 안했는지 확인하기 위함
            applyTag = true
            dismiss()
        }

        binding.myCourseWriteOptionLocationTv.setOnClickListener {
            binding.myCourseWriteOptionTagLayout.visibility = View.GONE
            binding.myCourseWriteOptionLocationLayout.visibility = View.VISIBLE
            binding.myCourseWriteOptionLocationTv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            binding.myCourseWriteOptionTasteTv.setTextColor(Color.parseColor("#999999"))
            binding.myCourseOptionSelectLayout.visibility = View.VISIBLE
        }

        binding.myCourseWriteOptionTasteTv.setOnClickListener {
            binding.myCourseWriteOptionTagLayout.visibility = View.VISIBLE
            binding.myCourseWriteOptionLocationLayout.visibility = View.GONE
            binding.myCourseWriteOptionTasteTv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            binding.myCourseWriteOptionLocationTv.setTextColor(Color.parseColor("#999999"))
            binding.myCourseOptionSelectLayout.visibility = View.GONE
        }

        binding.myCourseWriteOptionX.setOnClickListener {
            dismiss()
        }
    }

    private fun initAdapter() {
        myCourseTagBottomPlaceRVAdapter = MyCourseTagButtonRVAdapter(placeTagList)
        binding.myCoursePlaceTagRv.adapter = myCourseTagBottomPlaceRVAdapter
        binding.myCoursePlaceTagRv.layoutManager = FlexboxLayoutManager(activity)
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
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomWithRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position + 9] = data.isChecked
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
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomTransRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position + 14] = data.isChecked
            }
        })

        transTagList.add(TagButton(15, resources.getString(R.string.trans_tag15), tagFlag[14]))
        transTagList.add(TagButton(16, resources.getString(R.string.trans_tag16), tagFlag[15]))
        transTagList.add(TagButton(17, resources.getString(R.string.trans_tag17), tagFlag[16]))
        transTagList.add(TagButton(18, resources.getString(R.string.trans_tag18), tagFlag[17]))
        transTagList.add(TagButton(null, "", false))

        myCourseOptionLocationRVAdapter = MyCourseOptionLocationRVAdapter(locationList)
        binding.myCourseWriteCityRv.adapter = myCourseOptionLocationRVAdapter
        binding.myCourseWriteCityRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        myCourseOptionLocationRVAdapter.addAllItems(LocationTranslator.returnCitySelected())
        myCourseOptionLocationRVAdapter.setOnItemClickListener(object: MyCourseOptionLocationRVAdapter.OnItemClickListener {
            override fun onItemClickListener(data: CityData, position: Int) {
                selectCityFlag = data.city
                myCourseOptionRegionRVAdapter.addAllItems(LocationTranslator.returnRegionSelected(data.city))
                checkSelectedItem(selectCityFlag)
            }
        })

        myCourseOptionRegionRVAdapter = MyCourseOptionLocationRegionRVAdapter(regionList)
        binding.myCourseWriteRegionRv.adapter = myCourseOptionRegionRVAdapter
        binding.myCourseWriteRegionRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        myCourseOptionRegionRVAdapter.addAllItems(LocationTranslator.returnRegionSelected("서울"))
        myCourseOptionRegionRVAdapter.setOnItemClickListener(object: MyCourseOptionLocationRegionRVAdapter.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onItemClickListener(data: RegionData, position: Int) {
                var city = selectCityFlag
                var region = data.region

                if (!data.isSelected) {
                    myCourseOptionSelectLocationRVAdapter.addItem(LocationData(city, region))
                } else {
                    myCourseOptionSelectLocationRVAdapter.remove(LocationData(city, region))
                }

                binding.myCourseOptionSelectNumberTv.text = myCourseOptionSelectLocationRVAdapter.returnListSize().toString()
            }
        })

        myCourseOptionSelectLocationRVAdapter = MyCourseOptionLocationSelectRVAdapter(selectRegionList)
        binding.myCourseOptionSelectLocationRv.adapter = myCourseOptionSelectLocationRVAdapter
        val layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseOptionSelectLocationRv.layoutManager = layoutManager
        myCourseOptionSelectLocationRVAdapter.setOnItemClickListener(object: MyCourseOptionLocationSelectRVAdapter.OnItemClickListener {
            override fun onItemClickListener(data: LocationData, position: Int) {
                binding.myCourseOptionSelectNumberTv.text = (myCourseOptionSelectLocationRVAdapter.returnListSize() - 1).toString()
                myCourseOptionRegionRVAdapter.changeIsSelectedNoPos(data.region)
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
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 85 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun checkSelectedItem(city: String) {
        var cityList = LocationTranslator.returnRegionSelected(city)
        for (i in 0 until cityList.size) {
            if (myCourseOptionSelectLocationRVAdapter.returnAllItems().contains(LocationData(city, cityList[i].region))) {
                myCourseOptionRegionRVAdapter.changeIsSelected(i)
            }
        }
    }
}