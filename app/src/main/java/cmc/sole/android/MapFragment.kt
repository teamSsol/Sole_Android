package cmc.sole.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentMapBinding
import com.naver.maps.map.NaverMapSdk

class MapFragment: BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        NaverMapSdk.getInstance(requireActivity()).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initAfterBinding() {
        
    }
}