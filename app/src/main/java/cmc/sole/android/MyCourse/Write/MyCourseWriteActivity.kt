package cmc.sole.android.MyCourse.Write

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.HomeCourseDetailResult
import cmc.sole.android.Home.MyCourseWriteImage
import cmc.sole.android.Home.PlaceResponseDtos
import cmc.sole.android.Home.Retrofit.HomeCourseDetailView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.MyCourse.MyCourseWriteTagBottomFragment
import cmc.sole.android.MyCourse.PlaceInfoData
import cmc.sole.android.MyCourse.Retrofit.*
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.ToastDefault
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ActivityMyCourseWriteBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MyCourseWriteActivity: BaseActivity<ActivityMyCourseWriteBinding>(ActivityMyCourseWriteBinding::inflate),
    HomeCourseDetailView, ImageTestView, MyCourseAddView, MyCourseUpdateView {

    private lateinit var writeVM: MyCourseWriteViewModel
    private lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()
    private lateinit var locationImgRVAdapter: MyCourseWriteLocationImageRVAdapter
    private var imgList = ArrayList<MyCourseWriteImage>()
    private lateinit var placeRVAdapter: MyCourseWritePlaceRVAdapter
    private var placeList = ArrayList<PlaceInfoData>()
    private var placeInfoList = ArrayList<PlaceResponseDtos>()

    // MEMO: 코스 수정에만 사용
    var courseId = -1

    private var mainImageUri: Uri? = null
    private var locationImageUri: Uri? = null
    var index = 0

    var tagFlagList = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    var placeCategories = mutableSetOf<String>()
    var transCategories = mutableSetOf<String>()
    var withCategories = mutableSetOf<String>()

    private lateinit var myCourseService: MyCourseService
    private lateinit var homeService: HomeService

    companion object{
        const val REQ_GALLERY = 1
    }

    private val thumbnailImgResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            mainImageUri = result.data?.data
            mainImageUri?.let{
                Glide.with(this)
                    .load(mainImageUri).placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile).fitCenter()
                    .apply(RequestOptions().centerCrop())
                    .into(binding.myCourseWriteMainIv)
            }
        }
    }

    private val locationImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            locationImageUri = result.data?.data
            placeRVAdapter.sendImgUrl(locationImageUri!!, index)
        }
        // locationImgRVAdapter.addItem(MyCourseWriteImage(locationImageUri.toString(), locationImage))
    }

    override fun initAfterBinding() {
        // UPDATE: Intent로 모드 받고 작업 진행
        courseId = intent.getIntExtra("courseId", -1)

        initService()
        initAdapter()

        if (courseId != -1) {
            binding.myCourseWriteUploadBtn.text = "코스 수정하기"
            getCourseInfo()
        }
        initViewModel()
        initClickListener()
    }

    private fun initService() {
        myCourseService = MyCourseService()
        myCourseService.setImageTestView(this)
        myCourseService.setMyCourseAddView(this)

        homeService = HomeService()
        homeService.setHomeCourseDetailView(this)
    }

    private fun getCourseInfo() {
        homeService.getHomeDetailCourse(courseId)
    }

    private fun initViewModel() {
        writeVM = ViewModelProvider(this)[MyCourseWriteViewModel::class.java]
        writeVM.date.observe (this, Observer {
            binding.myCourseWriteDateTv.text = writeVM.getDate()
        })
    }

    private fun initClickListener() {
        binding.myCourseWriteBackIv.setOnClickListener {
            finish()
        }

        binding.myCourseWriteCourseImageCv.setOnClickListener {
            val writePermission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            val readPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)

            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), REQ_GALLERY)
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                thumbnailImgResult.launch(intent)
            }
        }

        binding.myCourseWriteDateLayout.setOnClickListener {
            val datePickerDialog = DialogMyCourseWriteDatePicker()
            datePickerDialog.show(this.supportFragmentManager, "MyCourseWriteDatePicker")
        }

        binding.myCourseWriteTagLayout.setOnClickListener {
            val myCourseWriteTagBottomFragment = MyCourseWriteTagBottomFragment()
            var bundle = Bundle()
            bundle.putBooleanArray("tagFlag", tagFlagList)
            myCourseWriteTagBottomFragment.arguments = bundle
            myCourseWriteTagBottomFragment.show(supportFragmentManager, "myCourseTagBottom")
            myCourseWriteTagBottomFragment.setOnFinishListener(object: MyCourseWriteTagBottomFragment.OnTagFragmentFinishListener {
                override fun finish(tagFragmentResult: List<TagButton>) {
                    for (i in 0..17) {
                        tagFlagList[i] = tagFragmentResult[i].isChecked
                    }

                    var tagArrayList = arrayListOf<String>()
                    for (i in 0..17) {
                        if (tagFlagList[i]) tagArrayList.add(tagFragmentResult[i].title)
                    }

                    tagArrayList.add("")
                    tagRVAdapter.addAllItems(tagArrayList)

                    placeCategories = returnCategories("PLACE")
                    transCategories = returnCategories("TRANS")
                    withCategories = returnCategories("WITH")
                }
            })
        }

        binding.myCourseWriteLocationAddCv.setOnClickListener {
            placeRVAdapter.addItem(PlaceInfoData(null, null, null, null, null, null, null))
        }

        binding.myCourseWriteUploadBtn.setOnClickListener {
            // if (courseId == -1) addCourse()
            // else updateCourse()
            addCourse()
        }
    }

    private fun addCourse() {
        var thumbnailImg = mutableListOf<MultipartBody.Part?>()

        var file: File
        var requestFile: RequestBody
        var multipartFile: MultipartBody.Part?

        if (mainImageUri != null) {
            file = File(absolutelyPath(Uri.parse(mainImageUri.toString()), this))
            // var test = File(mainImageUri.toString())
            Log.d("API-TEST", "mainImageUri = $mainImageUri")
            requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            multipartFile = MultipartBody.Part.createFormData("thumbnailImg", file.name, requestFile)
            thumbnailImg.add(multipartFile)
        }

        val placeRequestDtos = JSONArray()
        for (i in 0 until placeRVAdapter.getItemSize()) {
            var jsonObject = JSONObject()
            jsonObject.put("placeName", placeRVAdapter.getItem(i).placeName)
            jsonObject.put("duration", placeRVAdapter.getItem(i).duration)
            jsonObject.put("description", placeRVAdapter.getItem(i).description)
            jsonObject.put("address", placeRVAdapter.getItem(i).address)
            jsonObject.put("latitude", placeRVAdapter.getItem(i).latitude)
            jsonObject.put("longitude", placeRVAdapter.getItem(i).longitude)

            var file2: File
            var requestFile2: RequestBody
            var multipartFile2: MultipartBody.Part?
//            if (mainImageUri != null) {
//                Log.d("API-TEST", "mainImageUri = $mainImageUri")
//                file = File(absolutelyPath(Uri.parse(mainImageUri.toString()), this))
//                requestFile = RequestBody.create(MediaType.parse("image/*"), file)
//                multipartFile = MultipartBody.Part.createFormData("thumbnailImg", file.name, requestFile)
//                thumbnailImg.add(multipartFile)
//            }

            var placeUri: Uri = Uri.parse(placeRVAdapter.getItem(i).imgUrl.toString())
            if (placeUri != null) {
                // MEMO: 이미지 주소 연결
                file2 = File(absolutelyPath(Uri.parse(placeRVAdapter.getItem(i).imgUrl.toString()), this))
                requestFile2 = RequestBody.create(MediaType.parse("image/*"), file2)
                multipartFile2 = MultipartBody.Part.createFormData(placeRVAdapter.getItem(i).placeName.toString(), file2.name, requestFile2)
                thumbnailImg.add(multipartFile2)
            }

            placeRequestDtos.put(jsonObject)
        }

        var jsonBody = JSONObject()
        jsonBody.put("date", binding.myCourseWriteDateTv.text.substring(0, 4) + "-" + binding.myCourseWriteDateTv.text.substring(5, 7) + "-" + binding.myCourseWriteDateTv.text.substring(8))
        jsonBody.put("description", binding.myCourseWriteReviewEt.text)
        jsonBody.put("distance", 0)
        jsonBody.put("placeCategories", JSONArray(placeCategories.toTypedArray()))
        jsonBody.put("placeRequestDtos", placeRequestDtos)
        jsonBody.put("title", binding.myCourseWriteTitleEt.text)
        jsonBody.put("transCategories", JSONArray(transCategories.toTypedArray()))
        jsonBody.put("withCategories", JSONArray(withCategories.toTypedArray()))

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString())
        var courseRequestDto = MultipartBody.Part.createFormData("courseRequestDto", "courseRequestDto", requestBody)
        var thumbnailImgRequest: List<MultipartBody.Part?> = thumbnailImg

        Log.d("WRITE-TEST", "jsonBody = $jsonBody")

        // var courseImgRequest: List<MultipartBody.Part?> = courseImg
        // MEMO: 코스 추가
        myCourseService.addMyCourse(thumbnailImgRequest, courseRequestDto)
    }

    private fun updateCourse() {
        var thumbnailImg = mutableListOf<MultipartBody.Part?>()

        if (mainImageUri != null) {
            var file = File(absolutelyPath(Uri.parse(mainImageUri.toString()), this))
            var requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            thumbnailImg.add(MultipartBody.Part.createFormData("thumbnailImg", file.name, requestFile))
        }

        val placeUpdateRequestDtos = JSONArray()
        for (i in 0 until placeRVAdapter.getItemSize()) {
            var jsonObject = JSONObject()
            jsonObject.put("address", placeRVAdapter.getItem(i).address)
            jsonObject.put("description", placeRVAdapter.getItem(i).description)
            jsonObject.put("duration", placeRVAdapter.getItem(i).duration)
            jsonObject.put("latitude", placeRVAdapter.getItem(i).latitude)
            jsonObject.put("longitude", placeRVAdapter.getItem(i).longitude)
            jsonObject.put("placeId", placeInfoList[i].placeId)
            jsonObject.put("placeId", placeInfoList[i].placeImgUrls)
            jsonObject.put("placeName", placeRVAdapter.getItem(i).placeName)

            // MEMO: 이미지 주소 연결
            var file = File(absolutelyPath(Uri.parse(placeRVAdapter.getItem(i).imgUrl.toString()), this))
            var requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            thumbnailImg.add(MultipartBody.Part.createFormData(placeRVAdapter.getItem(i).placeName.toString(), file.name, requestFile))

            placeUpdateRequestDtos.put(jsonObject)
        }

        var jsonBody = JSONObject()
        jsonBody.put("description", binding.myCourseWriteReviewEt.text)
        jsonBody.put("placeCategories", JSONArray(placeCategories.toTypedArray()))
        jsonBody.put("placeUpdateRequestDtos", placeUpdateRequestDtos)
        jsonBody.put("startDate", binding.myCourseWriteDateTv.text.substring(0, 4) + "-" + binding.myCourseWriteDateTv.text.substring(5, 7) + "-" + binding.myCourseWriteDateTv.text.substring(8))
        jsonBody.put("title", binding.myCourseWriteTitleEt.text)
        jsonBody.put("transCategories", JSONArray(transCategories.toTypedArray()))
        jsonBody.put("withCategories", JSONArray(withCategories.toTypedArray()))

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString())
        var courseUpdateRequestDto = MultipartBody.Part.createFormData("courseUpdateRequestDto", "courseRequestDto", requestBody)
        var thumbnailImgRequest: List<MultipartBody.Part?> = thumbnailImg

        Log.d("WRITE-TEST", "jsonBody = $jsonBody")

        // var courseImgRequest: List<MultipartBody.Part?> = courseImg
        // MEMO: 코스 수정
        myCourseService.updateMyCourse(courseId, thumbnailImgRequest, courseUpdateRequestDto)
    }

    private fun returnCategories(option: String): MutableSet<String> {
        var returnCategoriesArray = mutableSetOf<String>()
        Log.d("WRITE-TEST", "option = $option")
        when(option) {
            "PLACE" -> {
                for (i in 0..8) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1).name)
                    }
                }
            } "TRANS" -> {
                for (i in 9..13) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1).name)
                    }
                }
            } else -> {
                for (i in 14..17) {
                    if (tagFlagList[i]) {
                        returnCategoriesArray.add(Translator.returnTagEngStr(i + 1).name)
                    }
                }
            }
        }

        return returnCategoriesArray
    }

    private fun initAdapter() {
        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.myCourseWriteTagRv.adapter = tagRVAdapter
        val layoutManager = FlexboxLayoutManager(this)
        binding.myCourseWriteTagRv.layoutManager = layoutManager
        binding.myCourseWriteTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.myCourseWriteTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))
        tagRVAdapter.clearItems()

        placeRVAdapter = MyCourseWritePlaceRVAdapter(placeList)
        binding.myCourseWritePlaceRv.adapter = placeRVAdapter
        binding.myCourseWritePlaceRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.myCourseWritePlaceRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 30))
        placeRVAdapter.setOnItemClickListener(object: MyCourseWritePlaceRVAdapter.OnItemClickListener {
            override fun onItemClick(data: PlaceInfoData, position: Int) {
                if (placeRVAdapter.returnAlbumMode()) {
                    val writePermission = ContextCompat.checkSelfPermission(this@MyCourseWriteActivity, WRITE_EXTERNAL_STORAGE)
                    val readPermission = ContextCompat.checkSelfPermission(this@MyCourseWriteActivity, READ_EXTERNAL_STORAGE)

                    if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
                        // 권한 요청
                        ActivityCompat.requestPermissions(this@MyCourseWriteActivity, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), REQ_GALLERY)
                    } else {
                        // 권한이 있는 경우 갤러리 실행
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                        locationImageResult.launch(intent)
                    }
                    index = position
                }
            }
        })

        if (courseId == -1) {
            placeList.add(PlaceInfoData(null, null, null, null, null, null, null))
            placeList.add(PlaceInfoData(null, null, null, null, null, null, null))
        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    private fun returnTag(tagList: List<TagButton>): MutableSet<String> {
        var resultTagSet = mutableSetOf<String>()

        for (i in tagList.indices) {
            resultTagSet.add(Translator.tagKorToEng(this, tagList[i].title))
        }

        return resultTagSet
    }

    override fun onResume() {
        super.onResume()

        if (mainImageUri != null) {
            binding.myCourseWriteCourseImageTv.visibility = View.INVISIBLE
            binding.myCourseWriteCourseImageAddIv.visibility = View.INVISIBLE
        } else {
            binding.myCourseWriteCourseImageTv.visibility = View.VISIBLE
            binding.myCourseWriteCourseImageAddIv.visibility = View.VISIBLE
        }
    }

    override fun setImageTestSuccessView() { }

    override fun setImageTestFailureView() { }

    override fun setMyCourseAddSuccessView(myCourseAddResult: MyCourseAddResult) {
        Log.d("WRITE-TEST", "myCourseAddResult = $myCourseAddResult")

        finish()
        ToastDefault.createToast(this, "코스 기록을 완료했어요 :)")?.show()
    }

    override fun setMyCourseAddFailureView() {
        showToast("코스 추가 실패")
    }

    override fun homeCourseDetailSuccessView(homeCourseDetailResult: HomeCourseDetailResult) {
        Log.d("API-TEST", "homeCourseDetailResult = $homeCourseDetailResult")

        binding.myCourseWriteTitleEt.setText(homeCourseDetailResult.title)
        Log.d("API-TESt", "ThumbnailImg = ${homeCourseDetailResult.thumbnailImg.toString()}")
        Glide.with(this).load(homeCourseDetailResult.thumbnailImg).centerCrop().into(binding.myCourseWriteMainIv)
        Log.d("API-TESt", "ThumbnailImg = ${homeCourseDetailResult.thumbnailImg}")
        binding.myCourseWriteDateTv.text = homeCourseDetailResult.startDate
        binding.myCourseWriteReviewEt.setText(homeCourseDetailResult.description)

        for (i in 0 until homeCourseDetailResult.categories.size)
            tagRVAdapter.addItem(Translator.tagEngToKor(this, homeCourseDetailResult.categories.elementAt(i).toString()))

        tagRVAdapter.addItem("")

        var placeData = homeCourseDetailResult.placeResponseDtos
        for (i in 0 until homeCourseDetailResult.placeResponseDtos.size) {
            var placeIndexData = placeData[i]
            placeInfoList.add(PlaceResponseDtos(placeIndexData.address, placeIndexData.description, placeIndexData.duration, placeIndexData.latitude, placeIndexData.longitude, placeIndexData.placeId, placeIndexData.placeImgUrls, placeIndexData.placeName))
            placeRVAdapter.addItem(PlaceInfoData(placeIndexData.address, placeIndexData.description, placeIndexData.duration, placeIndexData.latitude, placeIndexData.longitude, placeIndexData.placeName, placeIndexData.placeImgUrls))
        }
    }

    override fun homeCourseDetailFailureView() {
        showToast("코스 정보 불러오기 실패")
    }

    override fun setMyCourseUpdateSuccessView() {
    }

    override fun setMyCourseUpdateFailureView() {
        showToast("코스 수정 실패")
    }
}