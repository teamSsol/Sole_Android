package cmc.sole.android.MyCourse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemMyCourseTagBinding

class MyCourseTagRVAdapter(private val tagList: ArrayList<String>): RecyclerView.Adapter<MyCourseTagRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseTagRVAdapter.ViewHolder {
        val binding: ItemMyCourseTagBinding = ItemMyCourseTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseTagRVAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int = tagList.size

    inner class ViewHolder(val binding: ItemMyCourseTagBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            if (tag == "") {
                binding.itemMyCourseTagCv.visibility = View.GONE
            } else binding.itemMyCourseTagTv.text = tag
        }
    }
}