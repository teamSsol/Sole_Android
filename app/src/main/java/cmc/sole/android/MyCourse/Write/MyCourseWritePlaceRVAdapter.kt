package cmc.sole.android.MyCourse.Write

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.MyCourseWriteImage
import cmc.sole.android.Home.locationAddImage
import cmc.sole.android.Home.locationImage
import cmc.sole.android.MyCourse.PlaceInfoData
import cmc.sole.android.MyCourse.Write.Search.MyCourseWriteSearchBottomFragment
import cmc.sole.android.MyCourse.Write.Search.SearchResultData
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.databinding.ItemMyCourseWritePlaceBinding
import com.bumptech.glide.Glide
import com.naver.maps.geometry.Tm128

public class MyCourseWritePlaceRVAdapter(private val placeInfoList: ArrayList<PlaceInfoData>): RecyclerView.Adapter<MyCourseWritePlaceRVAdapter.ViewHolder>() {

    // MEMO: 여러 장의 장소 이미지를 위한 Adapter
    private lateinit var locationImgRVAdapter: MyCourseWriteLocationImageRVAdapter
    private lateinit var itemClickListener: OnItemClickListener
    var albumMode = false

    interface OnItemClickListener {
        fun onItemClick(data: MyCourseWriteImage, position: Int)
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

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        locationImgRVAdapter = MyCourseWriteLocationImageRVAdapter(placeInfoList[position].imgUrl)
        holder.binding.myCourseWriteLocationRv.adapter = locationImgRVAdapter
        holder.binding.myCourseWriteLocationRv.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        // holder.binding.myCourseWriteLocationRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        locationImgRVAdapter.setOnItemClickListener(object: MyCourseWriteLocationImageRVAdapter.OnItemClickListener {
            override fun onLocationItemClick(data: MyCourseWriteImage, imgPosition: Int) {
                removeImage(position, imgPosition)
            }

            override fun onLocationAddItemClick(data: MyCourseWriteImage, imgPosition: Int) {
                checkAlbumMode(true)
                itemClickListener.onItemClick(placeInfoList[position].imgUrl[imgPosition], position)
                checkAlbumMode(false)
            }
        })
        
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
                        val tm128 = Tm128(result.mapx.toDouble(), result.mapy.toDouble())
                        val point = tm128.toLatLng()
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
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(placeInfo: PlaceInfoData) {
        placeInfoList.add(placeInfo)
        this.notifyItemInserted(placeInfoList.size - 1)
    }

    private fun checkAlbumMode(mode: Boolean) {
        albumMode = mode
    }

    fun returnAlbumMode(): Boolean {
        return albumMode
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sendImgUrl(imgUrl: Uri, position: Int) {
        // MEMO: 사진 추가 이미지를 제일 뒤로 보내기 위함
        placeInfoList[position].imgUrl.removeAt(placeInfoList[position].imgUrl.size - 1)
        placeInfoList[position].imgUrl.add(MyCourseWriteImage(imgUrl.toString(), locationImage))

        if (placeInfoList[position].imgUrl.size < 4) {
            placeInfoList[position].imgUrl.add(MyCourseWriteImage("", locationAddImage))
        }
        locationImgRVAdapter.notifyDataSetChanged()
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeImage(position: Int, imgPosition: Int) {
        placeInfoList[position].imgUrl.removeAt(imgPosition)
        if (imgPosition == 3) {
            placeInfoList[position].imgUrl.add(MyCourseWriteImage("", locationAddImage))
        }

        locationImgRVAdapter.notifyDataSetChanged()
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