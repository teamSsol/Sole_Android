package cmc.sole.android.Write.Search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemMyCourseWriteSearchResultBinding

class MyCourseSearchResultRVAdapter(private val searchResultList: ArrayList<SearchResultData>): RecyclerView.Adapter<MyCourseSearchResultRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: SearchResultData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemMyCourseWriteSearchResultBinding = ItemMyCourseWriteSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(searchResultList[position], position)
        }
        holder.bind(searchResultList[position])
    }

    override fun getItemCount(): Int = searchResultList.size

    inner class ViewHolder(val binding: ItemMyCourseWriteSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchResult: SearchResultData) {
            binding.myCourseWriteSearchResultTitle.text = searchResult.title
            binding.myCourseWriteSearchResultRoadAddress.text = searchResult.roadAddress
            binding.myCourseWriteSearchResultAddress.text = searchResult.address
        }
    }

    fun addItem(searchResult: SearchResultData) {
        searchResultList.add(searchResult)
        this.notifyDataSetChanged()
    }

    fun addAllItems(searchResult: ArrayList<SearchResultData>) {
        searchResultList.addAll(searchResult)
        this.notifyDataSetChanged()
    }
}