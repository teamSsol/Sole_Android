package cmc.sole.android.MyCourse.Write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.MyCourseWriteImage
import cmc.sole.android.Home.locationAddImage
import cmc.sole.android.Home.locationImage
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemMyCourseWriteLocationAddImageBinding
import cmc.sole.android.databinding.ItemMyCourseWriteLocationImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MyCourseWriteLocationImageRVAdapter(private val imgList: ArrayList<MyCourseWriteImage>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: MyCourseWriteImage, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return imgList[position].viewType!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            locationImage -> {
                val binding: ItemMyCourseWriteLocationImageBinding = ItemMyCourseWriteLocationImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyCourseWriteLocationImageViewHolder(binding)
            } else -> {
                val binding: ItemMyCourseWriteLocationAddImageBinding = ItemMyCourseWriteLocationAddImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyCourseWriteLocationAddImageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (imgList[position].viewType) {
            locationImage -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onItemClick(imgList[position], position)
                }
                (holder as MyCourseWriteLocationImageViewHolder).bind(imgList[position])
                holder.setIsRecyclable(false)
            } else -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onItemClick(imgList[position], position)
                }
                (holder as MyCourseWriteLocationAddImageViewHolder).bind(imgList[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = imgList.size

    inner class MyCourseWriteLocationImageViewHolder(val binding: ItemMyCourseWriteLocationImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(img: MyCourseWriteImage) {
            Glide.with(binding.root.context)
                .load(img.imgUrl).fitCenter()
                .apply(RequestOptions().centerCrop())
                .into(binding.myCourseWriteLocationImageIv)

            binding.myCourseWriteLocationImageIv.setImageResource(R.drawable.test_img)
        }
    }

    inner class MyCourseWriteLocationAddImageViewHolder(val binding: ItemMyCourseWriteLocationAddImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(img: MyCourseWriteImage) { }
    }

    fun addItem(item: MyCourseWriteImage) {
        imgList.removeAt(imgList.size - 1)
        imgList.add(item)

        if (imgList.size < 4) {
            imgList.add(MyCourseWriteImage("", locationAddImage))
        }
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: java.util.ArrayList<MyCourseWriteImage>) {
        imgList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        imgList.removeAt(position)
        if (imgList.size < 4) {
            imgList.add(MyCourseWriteImage("", locationAddImage))
        }
        this.notifyDataSetChanged()
    }
}