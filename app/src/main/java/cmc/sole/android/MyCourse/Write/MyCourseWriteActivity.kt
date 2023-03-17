package cmc.sole.android.MyCourse.Write

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.MyCourseWriteImage
import cmc.sole.android.Home.locationAddImage
import cmc.sole.android.Home.locationImage
import cmc.sole.android.MyCourse.MyCourseTagBottomFragment
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.MyCourse.PlaceInfoData
import cmc.sole.android.MyCourse.Write.Search.MyCourseWriteSearchBottomFragment
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.ToastDefault
import cmc.sole.android.databinding.ActivityMyCourseWriteBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager

class MyCourseWriteActivity: BaseActivity<ActivityMyCourseWriteBinding>(ActivityMyCourseWriteBinding::inflate) {

    private lateinit var writeVM: MyCourseWriteViewModel
    private lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()
    private lateinit var locationImgRVAdapter: MyCourseWriteLocationImageRVAdapter
    private var imgList = ArrayList<MyCourseWriteImage>()
    private lateinit var placeRVAdapter: MyCourseWritePlaceRVAdapter
    private var placeList = ArrayList<PlaceInfoData>()

    private var mainImageUri: Uri? = null
    private var locationImageUri: Uri? = null

    companion object{
        const val REQ_GALLERY = 1
    }

    private val mainImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        }
        locationImgRVAdapter.addItem(MyCourseWriteImage(locationImageUri.toString(), locationImage))
    }

    override fun initAfterBinding() {
        initViewModel()
        initClickListener()
        initAdapter()
    }

    private fun initViewModel() {
        writeVM = ViewModelProvider(this)[MyCourseWriteViewModel::class.java]
        writeVM.date.observe (this, Observer {
            binding.myCourseWriteDateTv.text = writeVM.getDate()
        })
        writeVM.time.observe (this, Observer {
            // binding.myCourseWriteTimeTv.text = writeVM.getTime()
        })
        writeVM.tag.observe(this, Observer {
            if (writeVM.getTag() != null) {
                var tagResult = writeVM.getTag()
                for (i in 0 until writeVM.getTag()!!.size) {
                    tagResult?.get(i)?.let { it1 -> tagRVAdapter.addItem(it1.title) }
                }
            }
        })
        writeVM.placeInfo.observe(this, Observer {
//             if (writeVM.getPlaceInfo() != null)
//                 binding.myCourseWriteTextEt.text = writeVM.getPlaceInfo()!!.title
        })
    }

    private fun initClickListener() {
        binding.myCourseWriteBackIv.setOnClickListener {
            finish()
        }

//        binding.myCourseWriteCourseImageCv.setOnClickListener {
//            val writePermission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
//            val readPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
//
//            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
//                // 권한 요청
//                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), REQ_GALLERY)
//            } else {
//                // 권한이 있는 경우 갤러리 실행
//                val intent = Intent(Intent.ACTION_PICK)
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
//                mainImageResult.launch(intent)
//            }
//        }

        binding.myCourseWriteDateLayout.setOnClickListener {
            val datePickerDialog = DialogMyCourseWriteDatePicker()
            datePickerDialog.show(this.supportFragmentManager, "MyCourseWriteDatePicker")
        }

        binding.myCourseWriteTagLayout.setOnClickListener {
            val myCourseTagBottomFragment = MyCourseTagBottomFragment()
            myCourseTagBottomFragment.show(supportFragmentManager, "myCourseTagBottom")
        }

//        binding.myCourseWriteSearchBar.setOnClickListener {
//            val myCourseWriteSearchBottomFragment = MyCourseWriteSearchBottomFragment()
//            myCourseWriteSearchBottomFragment.show(this.supportFragmentManager, "MyCourseWriteSearchBottom")
//        }
//
//        binding.myCourseWriteTimeLayout.setOnClickListener {
//            val timePickerDialog = DialogMyCourseWriteTimePicker()
//            timePickerDialog.show(this.supportFragmentManager, "MyCourseWriteTimePicker")
//        }

        binding.myCourseWriteLocationAddCv.setOnClickListener {
            placeRVAdapter.addItem(PlaceInfoData(null, null, null, null, null, null))
        }

        binding.myCourseWriteUploadBtn.setOnClickListener {
            finish()
            ToastDefault.createToast(this, "코스 기록을 완료했어요 :)")?.show()
        }
    }

    private fun initAdapter() {
        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.myCourseWriteTagRv.adapter = tagRVAdapter
        val layoutManager = FlexboxLayoutManager(this)
        binding.myCourseWriteTagRv.layoutManager = layoutManager
        binding.myCourseWriteTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.myCourseWriteTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))
        tagRVAdapter.clearItems()

//        locationImgRVAdapter = MyCourseWriteLocationImageRVAdapter(imgList)
//        binding.myCourseWriteLocationRv.adapter = locationImgRVAdapter
//        binding.myCourseWriteLocationRv.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.HORIZONTAL, false)
//        binding.myCourseWriteLocationRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
//        locationImgRVAdapter.setOnItemClickListener(object:
//            MyCourseWriteLocationImageRVAdapter.OnItemClickListener {
//            override fun onItemClick(data: MyCourseWriteImage, position: Int) {
//                if (data.imgUrl == "") {
//                    val writePermission = ContextCompat.checkSelfPermission(this@MyCourseWriteActivity, WRITE_EXTERNAL_STORAGE)
//                    val readPermission = ContextCompat.checkSelfPermission(this@MyCourseWriteActivity, READ_EXTERNAL_STORAGE)
//
//                    if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
//                        ActivityCompat.requestPermissions(this@MyCourseWriteActivity, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), REQ_GALLERY)
//                    } else {
//                        val intent = Intent(Intent.ACTION_PICK)
//                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
//                        locationImageResult.launch(intent)
//                    }
//                } else {
//                    locationImgRVAdapter.removeItem(position)
//                }
//            }
//        })
//
//        imgList.add(MyCourseWriteImage("", locationAddImage))

        placeRVAdapter = MyCourseWritePlaceRVAdapter(placeList)
        binding.myCourseWritePlaceRv.adapter = placeRVAdapter
        binding.myCourseWritePlaceRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.myCourseWritePlaceRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 30))

        placeList.add(PlaceInfoData(null, null, null, null, null, null))
    }

    private fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)
        return result!!
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
}