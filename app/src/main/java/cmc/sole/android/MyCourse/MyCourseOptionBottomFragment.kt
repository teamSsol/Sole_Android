package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cmc.sole.android.MyCourse.Write.MyCourseWriteViewModel
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteOptionNewBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyCourseOptionBottomFragment: BottomSheetDialogFragment() {

    // lateinit var binding: BottomFragmentMyCourseWriteOptionBinding
    lateinit var binding: BottomFragmentMyCourseWriteOptionNewBinding
    private lateinit var myCourseTagBottomPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomTransRVAdapter: MyCourseTagButtonRVAdapter
    private var locationList = HashMap<String, ArrayList<String>>()
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var checkTagList: MutableList<TagButton> = mutableListOf()
    private lateinit var dialogFinishListener: OnTagFragmentFinishListener
    private var sendTag = listOf<TagButton>()
    private var tagFlag = booleanArrayOf()

    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    interface OnTagFragmentFinishListener {
        fun finish(tagSort: List<TagButton>)
    }

    fun setOnFinishListener(listener: OnTagFragmentFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
        Log.d("WRITE-TEST", "returnList = $returnList")
        dialogFinishListener.finish(returnList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentMyCourseWriteOptionNewBinding.inflate(inflater, container, false)
        // checkTagList = writeVM.getTag()

        tagFlag = requireArguments().getBooleanArray("tagFlag")!!

        initSetLocationList()
        initAdapter()
        initClickListener()

        return binding.root
    }

    private fun initSetLocationList() {
        // 서울
        locationList.put("서울", arrayListOf("전체",
            "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구",
            "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
            "용산구", "은평구", "종로구", "중구", "중랑구"
        ))
        
        locationList.put("경기", arrayListOf("전체",
            "가평군", "고양", "과천", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시",
            "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시",
            "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시",
            "화성시"
        ))

        locationList.put("인천", arrayListOf("전체",
            "강화군", "계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "옹진군", "중구"
        ))

        locationList.put("대전", arrayListOf("전체",
            "대덕구", "동구", "서구", "유성구", "중구"
        ))

        locationList.put("세종", arrayListOf("전체",
        ))

        locationList.put("충남", arrayListOf("전체",
            "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군",
            "천안시", "청양군", "태안군", "홍성군", "계룡시"
        ))

        locationList.put("충북", arrayListOf("전체",
            "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시", "진천군", "청주시", "충주시",
            "증평군"
        ))

        locationList.put("광주", arrayListOf("전체",
            "광산구", "남구", "동구", "북구", "서구"
        ))

        locationList.put("전남", arrayListOf("전체",
            "강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군", "보성군",
            "순천시", "신안군", "여수시", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군",
            "해남군", "화순군"
        ))

        locationList.put("전북", arrayListOf("전체",
            "고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", "임실군",
            "장수군", "전주시", "정읍시", "진안군"
        ))

        locationList.put("대구", arrayListOf("전체",
            "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"
        ))

        locationList.put("경북", arrayListOf("전체",
            "경산시", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시", "봉화군", "상주시", "성주군",
            "안동시", "영덕군", "영양군", "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군",
            "청송군", "칠곡군", "포항시"
        ))

        locationList.put("부산", arrayListOf("전체",
            "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구",
            "서구", "수영구", "연제구", "영도구", "중구", "해운대구"
        ))

        locationList.put("울산", arrayListOf("전체",
            "남구", "동구", "북구", "울주군", "중구"
        ))

        locationList.put("경남", arrayListOf("전체",
            "거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시", "의령군",
            "진주시", "창녕군", "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"
        ))

        locationList.put("강원", arrayListOf("전체",
            "강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군", "원주시", "인제군",
            "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군"
        ))

        locationList.put("제주", arrayListOf("전체",
            "서귀포시", "제주시"
        ))
    }

    private fun initClickListener() {
        binding.myCourseTagOkBtn.setOnClickListener {
            var tagResult: ArrayList<TagButton> = arrayListOf()

            if (writeVM.getTag() != null) {
                var tagCheckList = writeVM.getTag()!!
                for (i in 0 until writeVM.getTag()!!.size - 1) {
                    tagResult.add(tagCheckList[i])
                }
            }

            for (element in checkTagList) {
                tagResult.add(element)
            }
            tagResult.add(TagButton(1000, "", false))

            writeVM.setTag(tagSort(tagResult))

            sendTag = tagSort(tagResult)

            for (i in 0..17) {
                Log.d("WRITE-TEST", "tagCheck = $i ${tagFlag[i]}")
            }
            dismiss()
        }

        binding.myCourseWriteOptionLocationTv.setOnClickListener {
            binding.myCourseWriteOptionTagLayout.visibility = View.GONE
            binding.myCourseWriteOptionLocationLayout.visibility = View.VISIBLE
        }

        binding.myCourseWriteOptionTasteTv.setOnClickListener {
            binding.myCourseWriteOptionTagLayout.visibility = View.VISIBLE
            binding.myCourseWriteOptionLocationLayout.visibility = View.GONE
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
                Log.d("WRITE-TEST", "$tagFlag")
//                if (data.isChecked) {
//                    checkTagList.add(data)
//                }
//                else {
//                    for (i in 0 until checkTagList.size) {
//                        if (data.title == checkTagList[i].title) {
//                            checkTagList.removeAt(i)
//                            break
//                        }
//                    }
//                }
//                writeVM.setCheckTag(position)
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
                tagFlag[position] = data.isChecked
                Log.d("WRITE-TEST", "$tagFlag")

//                if (data.isChecked) {
//                    checkTagList.add(data)
//                }
//                else {
//                    for (i in 0 until checkTagList.size) {
//                        if (data.title == checkTagList[i].title) {
//                            checkTagList.removeAt(i)
//                            break
//                        }
//                    }
//                }
//                writeVM.setCheckTag(position + 9)
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
                tagFlag[position] = data.isChecked
                Log.d("WRITE-TEST", "$tagFlag")
            }
        })

        transTagList.add(TagButton(15, resources.getString(R.string.trans_tag15), tagFlag[14]))
        transTagList.add(TagButton(16, resources.getString(R.string.trans_tag16), tagFlag[15]))
        transTagList.add(TagButton(17, resources.getString(R.string.trans_tag17), tagFlag[16]))
        transTagList.add(TagButton(18, resources.getString(R.string.trans_tag18), tagFlag[17]))
        transTagList.add(TagButton(null, "", false))
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
        return getWindowHeight() * 85 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun tagSort(list: List<TagButton>): List<TagButton> {
        return list.sortedBy { it.index }
    }
}