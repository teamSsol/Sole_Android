package cmc.sole.android.MyCourse.Write

import android.R
import android.app.Activity
import android.content.Context
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

public class MyCourseWritePlaceRVAdapter(private val placeInfoList: ArrayList<PlaceInfoData>): RecyclerView.Adapter<MyCourseWritePlaceRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    var imgList = ArrayList<MyCourseWriteImage>()

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
        locationImgRVAdapter.setOnItemClickListener(object: MyCourseWriteLocationImageRVAdapter.OnItemClickListener {
            override fun onItemClick(data: MyCourseWriteImage, position: Int) {
                Log.d("WRITE-TEST", "imageItemClickTest")
            }
        })
        locationImgRVAdapter.addItem(MyCourseWriteImage("", locationAddImage))

        holder.binding.myCourseWriteSearchBar.setOnClickListener {
            val myCourseWriteSearchBottomFragment = MyCourseWriteSearchBottomFragment()
             myCourseWriteSearchBottomFragment.show((holder.binding.root.context as FragmentActivity).supportFragmentManager, "MyCourseWriteSearchBottom")
        }
        holder.binding.myCourseWriteTimeLayout.setOnClickListener {
            val timePickerDialog = DialogMyCourseWriteTimePicker()
            timePickerDialog.show((holder.binding.root.context as FragmentActivity).supportFragmentManager, "MyCourseWriteTimePicker")
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