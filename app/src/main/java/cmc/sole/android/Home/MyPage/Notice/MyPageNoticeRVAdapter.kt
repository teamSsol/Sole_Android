package cmc.sole.android.Home.MyPage.Notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.MyPageNoticeResult
import cmc.sole.android.databinding.ItemMyPageNoticeListBinding

class MyPageNoticeRVAdapter(private val noticeList: ArrayList<MyPageNoticeResult>): RecyclerView.Adapter<MyPageNoticeRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: MyPageNoticeResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageNoticeRVAdapter.ViewHolder {
        val binding: ItemMyPageNoticeListBinding = ItemMyPageNoticeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageNoticeRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(noticeList[position], position)
        }

        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int = noticeList.size

    inner class ViewHolder(private val binding: ItemMyPageNoticeListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: MyPageNoticeResult) {
            binding.noticeListTitle.text = notice.title
        }
    }

    fun addItem(item: MyPageNoticeResult) {
        if (item != null) {
            noticeList.add(item)
        }
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<MyPageNoticeResult>) {
        noticeList.addAll(items)
        this.notifyDataSetChanged()
    }
}