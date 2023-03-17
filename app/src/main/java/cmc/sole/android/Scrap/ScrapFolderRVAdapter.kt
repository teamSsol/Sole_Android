package cmc.sole.android.Scrap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Follow.FollowListData
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemScrapFolderAddBinding
import cmc.sole.android.databinding.ItemScrapFolderBinding

class ScrapFolderRVAdapter(private val folderList: ArrayList<ScrapFolderData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(scrapFolder: ScrapFolderData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return folderList[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            defaultFolder -> {
                val binding = ItemScrapFolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DefaultFolderViewHolder(binding)
            }
            addFolder -> {
                val binding = ItemScrapFolderAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AddFolderViewHolder(binding)
            }
            else -> {
                val binding = ItemScrapFolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DefaultFolderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (folderList[position].viewType) {
            defaultFolder -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onItemClick(folderList[position], position)
                }
                (holder as DefaultFolderViewHolder).bind(folderList[position])
                holder.setIsRecyclable(false)
            }
            addFolder -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onItemClick(folderList[position], position)
                }
                (holder as AddFolderViewHolder).bind(folderList[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onItemClick(folderList[position], position)
                }
                (holder as DefaultFolderViewHolder).bind(folderList[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = folderList.size

    inner class DefaultFolderViewHolder(val binding: ItemScrapFolderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapFolder: ScrapFolderData) {
            // UPDATE: Image 연결
            if (scrapFolder.title == "가족")
                binding.itemScarpFolderIv.setImageResource(R.drawable.test_img)
            else if (scrapFolder.title == "친구")
                binding.itemScarpFolderIv.setImageResource(R.drawable.test_img_2)
            else if (scrapFolder.title == "기본 폴더")
                binding.itemScarpFolderIv.setImageResource(R.drawable.test_img_3)

            binding.itemScrapFolderTv.text = scrapFolder.title
        }
    }

    inner class AddFolderViewHolder(val binding: ItemScrapFolderAddBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapFolder: ScrapFolderData) {
            if (scrapFolder.title == "") {
                binding.itemScrapFolderAddLayout.visibility = View.GONE
                binding.itemScrapFolderTv.visibility = View.GONE
            }
        }
    }
}