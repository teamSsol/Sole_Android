package cmc.sole.android.MyCourse

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.Utils.LocationData
import cmc.sole.android.Utils.RegionData
import cmc.sole.android.databinding.ItemMyCourseOptionLocationSelectBinding
import cmc.sole.android.databinding.ItemMyCourseOptionRegionBinding
import java.util.function.Predicate

class MyCourseOptionLocationSelectRVAdapter(private val locationList: ArrayList<LocationData>): RecyclerView.Adapter<MyCourseOptionLocationSelectRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private var preSelectedIndex = -1

    interface OnItemClickListener {
        fun onItemClickListener(data: LocationData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseOptionLocationSelectRVAdapter.ViewHolder {
        val binding: ItemMyCourseOptionLocationSelectBinding = ItemMyCourseOptionLocationSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyCourseOptionLocationSelectRVAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.root.setOnClickListener {
            itemClickListener.onItemClickListener(locationList[position], position)
            locationList.remove(locationList[position])
            this.notifyDataSetChanged()
        }
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int = locationList.size

    inner class ViewHolder(val binding: ItemMyCourseOptionLocationSelectBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(location: LocationData) {
            binding.locationSelectTv.text = "${location.city} > ${location.region}"
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: LocationData) {

        if (item.region == "전체") {
            locationList.removeIf {
                it.city == item.city
            }
        }
        else {
            locationList.removeIf {
                it.city == item.city && it.region == "전체"
            }
        }
        this.notifyDataSetChanged()

        locationList.add(item)
        this.notifyItemInserted(locationList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllItems(items: ArrayList<LocationData>) {
        locationList.clear()
        locationList.addAll(items)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(item: LocationData) {
        locationList.remove(item)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(city: String) {
        for (i in 0 until locationList.size) {
            if (locationList[i].city == city) {
                locationList.remove(LocationData(locationList[i].city, locationList[i].region))
                this.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        locationList.clear()
        this.notifyDataSetChanged()
    }

    fun returnListSize(): Int {
        return locationList.size
    }
}