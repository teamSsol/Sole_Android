package cmc.sole.android.Home

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.MyCourse.Retrofit.MyCourseHistoryRequest
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ActivityStartCourseTagBinding
import cmc.sole.android.databinding.BottomFragmentMyCourseTagBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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