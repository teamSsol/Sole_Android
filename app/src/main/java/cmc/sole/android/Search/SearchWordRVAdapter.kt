package cmc.sole.android.Search

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Search.RoomDB.SearchWord
import cmc.sole.android.databinding.ItemSearchBinding

class SearchWordRVAdapter(private val wordList: MutableList<SearchWord>): RecyclerView.Adapter<SearchWordRVAdapter.ViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    var mode = -1

    interface OnItemClickListener {
        fun onClick(data: SearchWord, position: Int)
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemSearchXIv.setOnClickListener {
            mode = 0
            onItemClickListener.onClick(wordList[position], position)
        }
        holder.itemView.setOnClickListener {
            mode = 1
            onItemClickListener.onClick(wordList[position], position)
        }
        holder.bind(wordList[position])
    }

    override fun getItemCount(): Int = wordList.size

    inner class ViewHolder(val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchWord: SearchWord) {
            binding.itemSearchWordTv.text = searchWord.searchWord
        }
    }

    fun addItem(item: SearchWord) {
        wordList.add(item)
        this.notifyItemChanged(wordList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllItem(items: MutableList<SearchWord>) {
        wordList.addAll(items)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        wordList.removeAt(position)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllItem() {
        wordList.clear()
        this.notifyDataSetChanged()
    }

    fun returnListSize(): Int {
        return wordList.size
    }

    fun setClickMode() {
        if (mode == 0) {
            // MEMO: X 클릭
            
        } else if (mode == 1) {
            // MEMO: 단어 클릭

        }
    }

    fun returnMode(): Int {
        return mode
    }
}