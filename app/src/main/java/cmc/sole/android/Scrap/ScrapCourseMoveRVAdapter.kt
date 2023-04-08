package cmc.sole.android.Scrap

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.Scrap.Retrofit.ScrapCourseMoveResult
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDataResult
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import cmc.sole.android.databinding.ItemScrapMoveFolderBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.math.roundToInt

class ScrapCourseMoveRVAdapter(private val scrapCourseList: ArrayList<ScrapFolderDataResult>): RecyclerView.Adapter<ScrapCourseMoveRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: ScrapFolderDataResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemScrapMoveFolderBinding = ItemScrapMoveFolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(scrapCourseList[position], position)
        }
        holder.bind(scrapCourseList[position])
    }

    override fun getItemCount(): Int = scrapCourseList.size

    inner class ViewHolder(val binding: ItemScrapMoveFolderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapCourse: ScrapFolderDataResult) {
            // UPDATE: 이미지 연결 필요
            // Glide.with(binding.root.context).load(scrapCourse).centerCrop().into(binding.itemScrapMoveFolderIv)
            binding.itemScrapMoveFolderTv.text = scrapCourse.scrapFolderName
        }
    }

    fun addItem(item: ScrapFolderDataResult) {
        scrapCourseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<ScrapFolderDataResult>) {
        scrapCourseList.clear()
        scrapCourseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun removeAllItems() {
        scrapCourseList.clear()
    }

    fun getAllItems(): ArrayList<ScrapFolderDataResult> {
        return scrapCourseList
    }

    fun getItems(position: Int): ScrapFolderDataResult {
        return scrapCourseList[position]
    }

    fun removeItem(start: Int, end: Int?) {
        if (end != null) {
            for (i in start until end) {
                scrapCourseList.removeAt(i)
            }
        } else {
            scrapCourseList.removeAt(start)
        }
    }
}