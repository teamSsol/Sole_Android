package cmc.sole.android.MyCourse.Write

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.MyCourseWriteImage
import cmc.sole.android.Home.locationAddImage
import cmc.sole.android.MyCourse.PlaceInfoData
import cmc.sole.android.MyCourse.Write.Search.MyCourseWriteSearchBottomFragment
import cmc.sole.android.MyCourse.Write.Search.SearchResultData
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.databinding.ItemMyCourseWritePlaceBinding
import com.bumptech.glide.Glide
import com.naver.maps.geometry.Tm128

public class MyCourseWritePlaceRVAdapter(private val placeInfoList: ArrayList<PlaceInfoData>): RecyclerView.Adapter<MyCourseWritePlaceRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    var imgList = ArrayList<MyCourseWriteImage>()
    var albumMode = false
    var placeImg: Uri? = null

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
//        var locationImgRVAdapter = MyCourseWriteLocationImageRVAdapter(imgList)
//        holder.binding.myCourseWriteLocationRv.adapter = locationImgRVAdapter
//        holder.binding.myCourseWriteLocationRv.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
//        holder.binding.myCourseWriteLocationRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
//        locationImgRVAdapter.setOnItemClickListener(object: MyCourseWriteLocationImageRVAdapter.OnItemClickListener {
//            override fun onItemClick(data: MyCourseWriteImage, position: Int) {
//                // 여기서 연결 필요
//                setAlbumMode()
//            }
//        })
        // locationImgRVAdapter.addItem(MyCourseWriteImage("", locationAddImage))

        // UPDATE: RecyclerView로 업데이트 필요
        // MEMO: 이미지 정보
        holder.binding.myCourseWriteLocationAddImageCv.setOnClickListener {
            checkAlbumMode(true)
            itemClickListener.onItemClick(placeInfoList[position], position)
            checkAlbumMode(false)
        }
        
        // MEMO: 장소 정보
        holder.binding.myCourseWriteSearchBar.setOnClickListener {
            val myCourseWriteSearchBottomFragment = MyCourseWriteSearchBottomFragment()
            myCourseWriteSearchBottomFragment.show((holder.binding.root.context as FragmentActivity).supportFragmentManager, "MyCourseWriteSearchBottom")
            checkAlbumMode(false)
            myCourseWriteSearchBottomFragment.setOnFinishListener(object: MyCourseWriteSearchBottomFragment.OnFinishListener {
                override fun finish(result: SearchResultData?) {
                    if (result != null) {
                        holder.binding.myCourseWriteTextEt.text = result.title
                        placeInfoList[position].placeName = result.title
                        placeInfoList[position].description = result.category
                        placeInfoList[position].address = result.address
                        var tm128 = Tm128(result.mapx.toDouble(), result.mapy.toDouble())
                        var point = tm128.toLatLng()
                        placeInfoList[position].latitude = point.latitude
                        placeInfoList[position].longitude = point.longitude
                    }
                }
            })
        }
        
        // MEMO: 소요 시간
        holder.binding.myCourseWriteTimeLayout.setOnClickListener {
            checkAlbumMode(false)
            val timePickerDialog = DialogMyCourseWriteTimePicker()
            timePickerDialog.show((holder.binding.root.context as FragmentActivity).supportFragmentManager, "MyCourseWriteTimePicker")
            timePickerDialog.setOnFinishListener(object: DialogMyCourseWriteTimePicker.OnDialogFragmentFinishListener {
                override fun finish(hour:String, minute: String) {
                    holder.binding.myCourseWriteTimeTv.text = if (minute == "0") {
                        if (hour == "0") ""
                        else hour + "시간 "
                    } else if (hour == "0") {
                        minute + "분"
                    } else {
                        hour + "시간 " + minute + "분"
                    }

                    placeInfoList[position].duration = hour.toInt() * 60 + minute.toInt()
                    Log.d("WRITE-TEST", "placeInfoList = $placeInfoList")
                }
            })
        }
        
        holder.bind(placeInfoList[position])
    }

    override fun getItemCount(): Int = placeInfoList.size

    inner class ViewHolder(val binding: ItemMyCourseWritePlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: PlaceInfoData) {
            if (placeInfo.placeName != null && placeInfo.address != null) {
                binding.myCourseWriteTextEt.text = placeInfo.placeName
            }

            Log.d("API-TEST", "placeInfoImgUrl = ${placeInfo.imgUrl}")
            if (placeInfo.imgUrl != null) {
                if (placeInfo.imgUrl!!.size > 0)
                    Glide.with(binding.root.context).load(Uri.parse(placeInfo.imgUrl!!.get(0))).centerCrop().into(binding.myCourseWriteLocationAddImageIv)
            }
        }
    }

    fun addItem(placeInfo: PlaceInfoData) {
        placeInfoList.add(placeInfo)
        this.notifyDataSetChanged()
    }

    private fun checkAlbumMode(mode: Boolean) {
        albumMode = mode
    }

    fun returnAlbumMode(): Boolean {
        return albumMode
    }

    fun sendImgUrl(imgUrl: Uri, position: Int) {
        // placeInfoList[position].imgUrl?.clear()
        // placeInfoList[position].imgUrl.add(imgUrl.toString())
        placeInfoList[position].imgUrl = arrayListOf(imgUrl.toString())

        Log.d("WRITE-TEST", "placeInfoList = $placeInfoList")

        this.notifyDataSetChanged()
   }

    fun getItemSize(): Int {
        return placeInfoList.size
    }

    fun getItem(position: Int): PlaceInfoData {
        return placeInfoList[position]
    }

    fun returnItems(): ArrayList<PlaceInfoData> {
        return placeInfoList
    }
}