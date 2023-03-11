package cmc.sole.android.MyCourse

import android.app.Activity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.ToastDefault
import cmc.sole.android.databinding.ActivityMyCourseWriteBinding

class MyCourseWriteActivity: BaseActivity<ActivityMyCourseWriteBinding>(ActivityMyCourseWriteBinding::inflate) {
    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.myCourseWriteBackIv.setOnClickListener {
            finish()
        }

        binding.myCourseWriteSearchBar.setOnClickListener {
            val myCourseWriteBottomFragment = MyCourseWriteBottomFragment()
            myCourseWriteBottomFragment.show(this.supportFragmentManager, "MyCourseWriteBottom")
        }

        binding.myCourseWriteUploadBtn.setOnClickListener {
            // finish()
            ToastDefault.createToast(this, "코스 기록을 완료했어요 :)")?.show()
        }
    }
}