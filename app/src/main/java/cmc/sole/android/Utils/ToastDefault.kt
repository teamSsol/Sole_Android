package cmc.sole.android.Utils

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cmc.sole.android.R
import cmc.sole.android.databinding.ToastDefaultBinding

object ToastDefault {
    fun createToast(context: Context, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastDefaultBinding = DataBindingUtil.inflate(inflater, R.layout.toast_default, null, false)

        binding.toastMyCourseWriteTv.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM, 0, 16.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}