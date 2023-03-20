package cmc.sole.android.MyCourse

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemMyCourseTagButtonBinding

class MyCourseTagButtonRVAdapter(private val tagList: ArrayList<TagButton>): RecyclerView.Adapter<MyCourseTagButtonRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(data: TagButton, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseTagButtonRVAdapter.ViewHolder {
        val binding: ItemMyCourseTagButtonBinding = ItemMyCourseTagButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseTagButtonRVAdapter.ViewHolder, position: Int) {
        holder.binding.itemMyCourseTagBtn.setOnClickListener {
            if  (tagList[position].isChecked) {
                holder.binding.itemMyCourseTagBtn.setBackgroundResource(R.drawable.tag_x)
                holder.binding.itemMyCourseTagBtn.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.black))
            } else {
                holder.binding.itemMyCourseTagBtn.setBackgroundResource(R.drawable.tag_o)
                holder.binding.itemMyCourseTagBtn.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.white))
            }
            tagList[position].isChecked = !tagList[position].isChecked
            itemClickListener.onItemClick(tagList[position], position)

            Log.d("TAG-TEST", "tag content = ${tagList[position].title}")
        }
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int = tagList.size

    inner class ViewHolder(val binding: ItemMyCourseTagButtonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: TagButton) {
            if (tag.title == "") {
                binding.itemMyCourseTagBtn.visibility = View.GONE
            } else binding.itemMyCourseTagBtn.text = tag.title

            if (!tag.isChecked) {
                binding.itemMyCourseTagBtn.setBackgroundResource(R.drawable.tag_x)
                binding.itemMyCourseTagBtn.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            } else {
                binding.itemMyCourseTagBtn.setBackgroundResource(R.drawable.tag_o)
                binding.itemMyCourseTagBtn.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            }
        }
    }

    fun getItem(position: Int): TagButton {
        return tagList[position]
    }
}