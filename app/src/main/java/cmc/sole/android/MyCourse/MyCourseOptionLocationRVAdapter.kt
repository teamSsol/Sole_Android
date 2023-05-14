package cmc.sole.android.MyCourse

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.Utils.CityData
import cmc.sole.android.databinding.ItemMyCourseOptionLocationBinding

class MyCourseOptionLocationRVAdapter(private val locationList: ArrayList<CityData>): RecyclerView.Adapter<MyCourseOptionLocationRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private var preSelectedIndex = 0

    interface OnItemClickListener {
        fun onItemClickListener(data: CityData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseOptionLocationRVAdapter.ViewHolder {
        val binding: ItemMyCourseOptionLocationBinding = ItemMyCourseOptionLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseOptionLocationRVAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.myCourseCityTv.setOnClickListener {
            itemClickListener.onItemClickListener(locationList[position], position)
            setSelectIndex(position)
            preSelectedIndex = position
        }
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int = locationList.size

    inner class ViewHolder(val binding: ItemMyCourseOptionLocationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(city: CityData) {
            binding.myCourseCityTv.text = city.city

            if (city.isSelected) {
                binding.myCourseCityTv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.main))
                binding.myCourseCityTv.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                binding.myCourseCityTv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.myCourseCityTv.setBackgroundColor(Color.parseColor("#EDEDED"))
            }
        }
    }

    fun addItem(item: CityData) {
        locationList.add(item)
        this.notifyItemInserted(locationList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllItems(items: ArrayList<CityData>) {
        locationList.clear()
        locationList.addAll(items)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        locationList.clear()
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectIndex(position: Int) {
        locationList[preSelectedIndex].isSelected = false
        this.notifyItemChanged(preSelectedIndex)

        locationList[position].isSelected = true
        this.notifyItemChanged(position)
    }
}