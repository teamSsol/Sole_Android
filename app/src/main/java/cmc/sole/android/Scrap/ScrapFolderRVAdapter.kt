package cmc.sole.android.Scrap

import android.view.LayoutInflater
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
                itemClickListener.onItemClick(folderList[position], position)
                (holder as DefaultFolderViewHolder).defaultFolderbind(folderList[position])
                holder.setIsRecyclable(false)
            }
            addFolder -> {
                itemClickListener.onItemClick(folderList[position], position)
                (holder as AddFolderViewHolder).addFolderbind(folderList[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                itemClickListener.onItemClick(folderList[position], position)
                (holder as DefaultFolderViewHolder).defaultFolderbind(folderList[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = folderList.size

    inner class DefaultFolderViewHolder(val binding: ItemScrapFolderBinding): RecyclerView.ViewHolder(binding.root) {
        fun defaultFolderbind(scrapFolder: ScrapFolderData) {
            // UPDATE: Image 연결
            binding.itemScarpFolderIv.setImageResource(R.drawable.test_img)
            binding.itemScrapFolderTv.text = scrapFolder.title
        }
    }

    inner class AddFolderViewHolder(val binding: ItemScrapFolderAddBinding): RecyclerView.ViewHolder(binding.root) {
        fun addFolderbind(scrapFolder: ScrapFolderData) {
            binding.itemScrapFolderAddTv.text = scrapFolder.title
        }
    }
}