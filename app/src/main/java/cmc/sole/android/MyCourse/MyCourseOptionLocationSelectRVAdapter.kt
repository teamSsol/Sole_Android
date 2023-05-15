package cmc.sole.android.MyCourse

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.Utils.LocationData
import cmc.sole.android.Utils.RegionData
import cmc.sole.android.databinding.ItemMyCourseOptionLocationSelectBinding
import cmc.sole.android.databinding.ItemMyCourseOptionRegionBinding

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

    override fun onBindViewHolder(holder: MyCourseOptionLocationSelectRVAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.root.setOnClickListener {

        }
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int = locationList.size

    inner class ViewHolder(val binding: ItemMyCourseOptionLocationSelectBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationData) {
            binding.locationSelectTv.text = location.city + " > " + location.region
        }
    }

    fun addItem(item: LocationData) {
        locationList.add(item)
        this.notifyItemInserted(locationList.size - 1)
    }

    fun addAllItems(items: ArrayList<LocationData>) {
        locationList.clear()
        locationList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun remove(item: LocationData) {
        locationList.remove(item)
        this.notifyDataSetChanged()
    }

    fun remove(city: String) {
        for (i in 0 until locationList.size) {
            if (locationList[i].city == city) {
                locationList.remove(LocationData(locationList[i].city, locationList[i].region))
                this.notifyDataSetChanged()
            }
        }
    }

    fun clearItems() {
        locationList.clear()
        this.notifyDataSetChanged()
    }
}