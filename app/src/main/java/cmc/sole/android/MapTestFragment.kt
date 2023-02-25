package cmc.sole.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback


class MapTestFragment : Fragment(), OnMapReadyCallback {
    //지도 객체 변수
    private var mapView: MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(
            R.layout.fragment_map_test,
            container, false
        ) as ViewGroup
        mapView = rootView.findViewById<View>(R.id.navermap) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(naverMap: NaverMap) {
        //배경 지도 선택
        naverMap.mapType = NaverMap.MapType.Satellite

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)

        //위치 및 각도 조정
        val cameraPosition = CameraPosition(
            LatLng(33.38, 126.55),  // 위치 지정
            9.0,  // 줌 레벨
            45.0,  // 기울임 각도
            45.0 // 방향
        )
        naverMap.cameraPosition = cameraPosition
    }

    override fun onStart() {
        var addr: String
        super.onStart()
        mapView!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    companion object {
        fun newInstance(): MapTestFragment {
            return MapTestFragment()
        }
    }
}