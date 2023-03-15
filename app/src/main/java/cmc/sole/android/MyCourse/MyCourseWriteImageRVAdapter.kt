package cmc.sole.android.MyCourse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemMyCourseTagBinding
import cmc.sole.android.databinding.ItemMyCourseWriteLocationImageBinding

class MyCourseWriteImageRVAdapter(private val imgList: ArrayList<String>): RecyclerView.Adapter<MyCourseWriteImageRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseWriteImageRVAdapter.ViewHolder {
        val binding: ItemMyCourseWriteLocationImageBinding = ItemMyCourseWriteLocationImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseWriteImageRVAdapter.ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    override fun getItemCount(): Int = imgList.size

    inner class ViewHolder(val binding: ItemMyCourseWriteLocationImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(img: String) {

        }
    }
}