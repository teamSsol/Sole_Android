package cmc.sole.android.Home.MyPage.Notice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding
import cmc.sole.android.databinding.ItemMyPageNoticeListBinding

class MyPageNoticeRVAdapter(private val noticeList: ArrayList<NoticeData>): RecyclerView.Adapter<MyPageNoticeRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: NoticeData, position: Int)
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
        fun bind(notice: NoticeData) {
            binding.noticeListTitle.text = notice.title
        }
    }
}