package cmc.sole.android.MyCourse

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.Utils.RegionData
import cmc.sole.android.databinding.ItemMyCourseOptionRegionBinding

class MyCourseOptionLocationRegionRVAdapter(private val regionList: ArrayList<RegionData>): RecyclerView.Adapter<MyCourseOptionLocationRegionRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private var preSelectedIndex = -1

    interface OnItemClickListener {
        fun onItemClickListener(data: RegionData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseOptionLocationRegionRVAdapter.ViewHolder {
        val binding: ItemMyCourseOptionRegionBinding = ItemMyCourseOptionRegionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseOptionLocationRegionRVAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.myCourseOptionRegionTv.setOnClickListener {
            itemClickListener.onItemClickListener(regionList[position], position)
            setSelectIndex(position)
            preSelectedIndex = position
        }
        holder.bind(regionList[position])
    }

    override fun getItemCount(): Int = regionList.size

    inner class ViewHolder(val binding: ItemMyCourseOptionRegionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(region: RegionData) {
            binding.myCourseOptionRegionTv.text = region.region

            if (region.isSelected) {
                binding.myCourseOptionRegionTv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.main))
            } else {
                binding.myCourseOptionRegionTv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            }
        }
    }

    fun addItem(item: RegionData) {
        regionList.add(item)
        this.notifyItemInserted(regionList.size - 1)
    }

    fun addAllItems(items: ArrayList<RegionData>) {
        regionList.clear()
        regionList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun clearItems() {
        regionList.clear()
        this.notifyDataSetChanged()
    }

    // MEMO: 전체 선택 부분 선택/취소 처리
    @SuppressLint("NotifyDataSetChanged")
    fun setSelectIndex(position: Int) {
        if (preSelectedIndex == 0) {
            regionList[preSelectedIndex].isSelected = false
            this.notifyItemChanged(preSelectedIndex)
        }

        if (position == 0) {
            for (i in 1 until regionList.size) {
                regionList[i].isSelected = false
                this.notifyItemChanged(i)
            }
        }

        regionList[position].isSelected = !regionList[position].isSelected
        this.notifyItemChanged(position)
    }
}