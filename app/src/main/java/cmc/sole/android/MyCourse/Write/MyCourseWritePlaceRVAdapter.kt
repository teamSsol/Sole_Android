package cmc.sole.android.MyCourse.Write

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.MyCourseWriteImage
import cmc.sole.android.Home.locationAddImage
import cmc.sole.android.Home.locationImage
import cmc.sole.android.MyCourse.PlaceInfoData
import cmc.sole.android.MyCourse.Write.Search.MyCourseWriteSearchBottomFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.databinding.ItemMyCourseWritePlaceBinding

class MyCourseWritePlaceRVAdapter(private val placeInfoList: ArrayList<PlaceInfoData>): RecyclerView.Adapter<MyCourseWritePlaceRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    var imgList = ArrayList<MyCourseWriteImage>()

//    private val locationImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == AppCompatActivity.RESULT_OK) {
//            locationImageUri = result.data?.data
//        }
//        locationImgRVAdapter.addItem(MyCourseWriteImage(locationImageUri.toString(), locationImage))
//    }

    interface OnItemClickListener {
        fun onItemClick(data: PlaceInfoData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemMyCourseWritePlaceBinding = ItemMyCourseWritePlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var locationImgRVAdapter = MyCourseWriteLocationImageRVAdapter(imgList)
        holder.binding.myCourseWriteLocationRv.adapter = locationImgRVAdapter
        holder.binding.myCourseWriteLocationRv.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.myCourseWriteLocationRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        locationImgRVAdapter.setOnItemClickListener(object:
            MyCourseWriteLocationImageRVAdapter.OnItemClickListener {
            override fun onItemClick(data: MyCourseWriteImage, position: Int) {
                Log.d("WRITE-TEST", "imageItemClickTest")
//                if (data.imgUrl == "") {
//                    val writePermission = ContextCompat.checkSelfPermission(holder.itemView.parent, WRITE_EXTERNAL_STORAGE)
//                    val readPermission = ContextCompat.checkSelfPermission(MyCourseWriteActivity::class.java, READ_EXTERNAL_STORAGE)
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
            }
        })
        // imgList.add(MyCourseWriteImage("", locationAddImage))
        locationImgRVAdapter.addItem(MyCourseWriteImage("", locationAddImage))

        holder.binding.myCourseWriteSearchBar.setOnClickListener {
            val myCourseWriteSearchBottomFragment = MyCourseWriteSearchBottomFragment()
            // myCourseWriteSearchBottomFragment.show(holder.itemView.context.supportFragmentManager, "MyCourseWriteSearchBottom")
        }
        holder.binding.myCourseWriteTimeLayout.setOnClickListener {
            val timePickerDialog = DialogMyCourseWriteTimePicker()
            // timePickerDialog.show(this.supportFragmentManager, "MyCourseWriteTimePicker")
        }
        holder.bind(placeInfoList[position])
    }

    override fun getItemCount(): Int = placeInfoList.size

    inner class ViewHolder(val binding: ItemMyCourseWritePlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: PlaceInfoData) {
            if (placeInfo.title != null && placeInfo.address != null) {
                // MEMO: 사진 Glide 연결
                binding.myCourseWriteTextEt.text = placeInfo.title
            }
        }
    }

    fun addItem(placeInfo: PlaceInfoData) {
        placeInfoList.add(placeInfo)
        this.notifyDataSetChanged()
    }
}