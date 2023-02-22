package cmc.sole.android

import android.os.Bundle
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMapTestBinding
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient


class MapTestActivity: BaseActivity<ActivityMapTestBinding>(ActivityMapTestBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NaverMapSdk.getInstance(this).client = NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)
    }

    override fun initAfterBinding() {

    }
}