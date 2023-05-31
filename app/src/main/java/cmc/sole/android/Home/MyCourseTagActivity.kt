package cmc.sole.android.Home


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivityStartCourseTagBinding

class MyCourseTagActivity: AppCompatActivity() {

    lateinit var binding: ActivityStartCourseTagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartCourseTagBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.startCourseTagSkipTv.setOnClickListener {
            finish()
        }
    }
}