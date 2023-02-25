package cmc.sole.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay

class MapFragment: BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {

    override fun initAfterBinding() {

    }
}