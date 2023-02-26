package cmc.sole.android

import android.os.Bundle
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityCourseDetailBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback


class CourseDetailActivity: BaseActivity<ActivityCourseDetailBinding>(ActivityCourseDetailBinding::inflate),
    OnMapReadyCallback {


    override fun initAfterBinding() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)

        val fm = supportFragmentManager
//        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
//            ?: MapFragment().also {
//                fm.beginTransaction().add(R.id.map, it).commit()
//            }

        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment

        // mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: NaverMap) {

    }
}