package cmc.sole.android.MyCourse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import cmc.sole.android.databinding.ItemMyCourseCourseCheckBinding
import com.bumptech.glide.Glide

class ScrapCourseRVAdapter(private val scrapCourseList: ArrayList<ScrapCourseResult>): RecyclerView.Adapter<ScrapCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var itemLongClickListener: OnItemLongClickListener

    interface OnItemClickListener {
        fun onItemClick(data: ScrapCourseResult, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(data: ScrapCourseResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        itemLongClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScrapCourseRVAdapter.ViewHolder {
        val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrapCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(scrapCourseList[position], position)

            if (holder.binding.myCourseCourseCheckIv.visibility == View.VISIBLE) {
                holder.binding.myCourseCourseCheckIv.visibility = View.GONE
            } else {
                holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
            }
        }
        holder.bind(scrapCourseList[position])
    }

    override fun getItemCount(): Int = scrapCourseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapCourse: ScrapCourseResult) {
            Glide.with(binding.root.context).load(scrapCourse.thumbnailImg).into(binding.myCourseCourseIv)
            binding.myCourseCourseTitleTv.text = scrapCourse.title
            binding.myCourseCourseLocationTv.text = scrapCourse.address
            binding.myCourseCourseTimeTv.text = scrapCourse.duration.toString() + "시간 소요"
            binding.myCourseCourseDistanceTv.text = scrapCourse.distance.toString() + "km 이동"
            // TODO: 태그 추가하기
        }
    }

    fun addItem(item: ScrapCourseResult) {
        scrapCourseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<ScrapCourseResult>) {
        scrapCourseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun removeAllItems() {
        scrapCourseList.clear()
    }
}