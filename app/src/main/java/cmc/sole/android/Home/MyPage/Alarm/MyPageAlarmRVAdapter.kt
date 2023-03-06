package cmc.sole.android.Home.MyPage.Alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemMyPageAlarmListBinding

class MyPageAlarmRVAdapter(private val alarmList: ArrayList<AlarmData>): RecyclerView.Adapter<MyPageAlarmRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageAlarmRVAdapter.ViewHolder {
        val binding = ItemMyPageAlarmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageAlarmRVAdapter.ViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    override fun getItemCount(): Int = alarmList.size

    inner class ViewHolder(private val binding: ItemMyPageAlarmListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: AlarmData) {
            binding.myPageAlarmListIv.setImageResource(alarm.img)
            binding.myPageAlarmListTitle.text = alarm.title
            binding.myPageAlarmListTime.text = alarm.time
        }
    }
}