package cmc.sole.android.MyCourse.Write

import android.R
import android.app.Activity
import android.content.Context
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
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.databinding.ItemMyCourseWritePlaceBinding
import com.bumptech.glide.Glide

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
        holder.binding.myCourseWriteLocationAddImageCv.setOnClickListener {
            checkAlbumMode(true)
            itemClickListener.onItemClick(placeInfoList[position], position)
            checkAlbumMode(false)
        }
        holder.binding.myCourseWriteSearchBar.setOnClickListener {
            val myCourseWriteSearchBottomFragment = MyCourseWriteSearchBottomFragment()
             myCourseWriteSearchBottomFragment.show((holder.binding.root.context as FragmentActivity).supportFragmentManager, "MyCourseWriteSearchBottom")
            checkAlbumMode(false)
        }
        holder.binding.myCourseWriteTimeLayout.setOnClickListener {
            checkAlbumMode(false)
            val timePickerDialog = DialogMyCourseWriteTimePicker()
            timePickerDialog.show((holder.binding.root.context as FragmentActivity).supportFragmentManager, "MyCourseWriteTimePicker")
            timePickerDialog.setOnFinishListener(object: DialogMyCourseWriteTimePicker.OnDialogFragmentFinishListener {
                override fun finish(time: String) {
                    holder.binding.myCourseWriteTimeTv.text = time
                }
            })
        }
        holder.bind(placeInfoList[position])
    }

    override fun getItemCount(): Int = placeInfoList.size

    inner class ViewHolder(val binding: ItemMyCourseWritePlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: PlaceInfoData) {
            if (placeInfo.title != null && placeInfo.address != null) {
                binding.myCourseWriteTextEt.text = placeInfo.title
            }

            Log.d("WRITE-TEST", "1 placeInfo.imgList = ${placeInfo.imgList}")
            if (placeInfo.imgList.toString() != "[]") {
                Glide.with(binding.root.context).load(Uri.parse(placeInfo.imgList!![0])).centerCrop().into(binding.myCourseWriteLocationAddImageIv)
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
        Log.d("WRITE-TEST", "placeInfoList.size = ${placeInfoList.size}")
        Log.d("WRITE-TEST", "placeInfoList[0] = ${placeInfoList[0].imgList}")
        Log.d("WRITE-TEST", "placeInfoList[1] = ${placeInfoList[1].imgList}")

        placeInfoList[position].imgList?.clear()
        placeInfoList[position].imgList?.add(imgUrl.toString())

        this.notifyDataSetChanged()

        Log.d("WRITE-TEST", "placeInfoList[0] = ${placeInfoList[0].imgList}")
        Log.d("WRITE-TEST", "placeInfoList[1] = ${placeInfoList[1].imgList}")
    }
}