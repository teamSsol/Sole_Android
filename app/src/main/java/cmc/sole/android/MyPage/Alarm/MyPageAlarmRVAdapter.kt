package cmc.sole.android.MyPage.Alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.MyPageNotificationHistoryResult
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemMyPageAlarmListBinding

class MyPageAlarmRVAdapter(private val alarmList: ArrayList<MyPageNotificationHistoryResult?>): RecyclerView.Adapter<MyPageAlarmRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemMyPageAlarmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        alarmList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = alarmList.size

    inner class ViewHolder(private val binding: ItemMyPageAlarmListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: MyPageNotificationHistoryResult) {
            if (alarm.type == "FOLLOW")
                binding.myPageAlarmListIv.setImageResource(R.drawable.ic_alarm_follow)
            // UPDATE: 다른 TYPE도 추가해주기
            binding.myPageAlarmListTitle.text = alarm.title
            binding.myPageAlarmListTime.text = alarm.createdAt
        }
    }

    fun addItem(item: MyPageNotificationHistoryResult) {
        alarmList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<MyPageNotificationHistoryResult?>) {
        alarmList.addAll(items)
        this.notifyDataSetChanged()
    }
}