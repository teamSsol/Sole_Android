package cmc.sole.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityCourseDetailBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback


// class CourseDetailActivity: BaseActivity<ActivityCourseDetailBinding>(ActivityCourseDetailBinding::inflate),
class CourseDetailActivity: AppCompatActivity(),
    OnMapReadyCallback {

    lateinit var binding: ActivityCourseDetailBinding
    lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        setContentView(R.layout.activity_course_detail)
    }

    override fun onMapReady(p0: NaverMap) {

    }
}