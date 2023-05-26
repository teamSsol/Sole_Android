package cmc.sole.android.MyPage.FAQ

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemMyPageFaqListBinding

class MyPageFAQRVAdapter(private val FAQList: ArrayList<FAQData>): RecyclerView.Adapter<MyPageFAQRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: FAQData, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemMyPageFaqListBinding = ItemMyPageFaqListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(FAQList[position], position)

            if (holder.binding.faqListContent.visibility == View.GONE) {
                holder.binding.faqListIv.setImageResource(R.drawable.ic_arrow_up)
                holder.binding.faqListContent.visibility = View.VISIBLE
            } else if (holder.binding.faqListContent.visibility == View.VISIBLE) {
                holder.binding.faqListIv.setImageResource(R.drawable.ic_arrow_down)
                holder.binding.faqListContent.visibility = View.GONE
            }
        }
        holder.bind(FAQList[position])
    }

    override fun getItemCount(): Int = FAQList.size

    inner class ViewHolder(val binding: ItemMyPageFaqListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(FAQ: FAQData) {
            binding.faqListTitle.text = FAQ.title
            binding.faqListContent.text = FAQ.Content
        }
    }
}