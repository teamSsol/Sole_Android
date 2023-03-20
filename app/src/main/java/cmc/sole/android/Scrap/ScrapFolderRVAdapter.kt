package cmc.sole.android.Scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDataResult
import cmc.sole.android.Scrap.Retrofit.addFolder
import cmc.sole.android.Scrap.Retrofit.defaultFolder
import cmc.sole.android.databinding.ItemScrapFolderAddBinding
import cmc.sole.android.databinding.ItemScrapFolderBinding

class ScrapFolderRVAdapter(private val folderList: ArrayList<ScrapFolderDataResult>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(scrapFolder: ScrapFolderDataResult, position: Int)
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
        fun bind(scrapFolder: ScrapFolderDataResult) {
            // UPDATE: Image 연결
            // binding.itemScarpFolderIv.setImageResource(R.drawable.test_img)
            binding.itemScrapFolderTv.text = scrapFolder.scrapFolderName
        }
    }

    inner class AddFolderViewHolder(val binding: ItemScrapFolderAddBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapFolder: ScrapFolderDataResult) { }
    }

    fun addItem(item: ScrapFolderDataResult) {
        folderList.add(item)
        this.notifyItemInserted(folderList.size - 1)
    }

    fun addAllItems(items: ArrayList<ScrapFolderDataResult>) {
        folderList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun clearItems() {
        folderList.clear()
        this.notifyDataSetChanged()
    }
}