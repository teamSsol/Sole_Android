package cmc.sole.android.Home

import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityStartCourseTagBinding

class MyCourseTagActivity: BaseActivity<ActivityStartCourseTagBinding>(ActivityStartCourseTagBinding::inflate) {
    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.startCourseTagSkipTv.setOnClickListener {
            finish()
        }
    }
}