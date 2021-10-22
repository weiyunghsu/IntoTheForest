package com.weiyung.intotheforest.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.maps.route.extensions.drawMarker
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import com.weiyung.intotheforest.R

class MapRouteFragment : Fragment(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialized google maps
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0
        p0.uiSettings.isZoomControlsEnabled = true;  // 右下角的放大縮小功能
        p0.uiSettings.isCompassEnabled = true;       // 左上角的指南針，要兩指旋轉才會出現
        p0.uiSettings.isMapToolbarEnabled = true;    // 右下角的導覽及開啟 Google Map功能
        val source = LatLng(25.02752, 121.57083) //starting point (LatLng)
        val destination = LatLng(25.02720, 121.57315) // ending point (LatLng)

        googleMap?.run {
            moveCameraOnMap(latLng = source)
            drawMarker(location = source, context = requireContext(), title = "test marker")
            drawRouteOnMap(
                getString(R.string.google_maps_key),
                source = source,
                destination = destination,
                context = context!!
            )
        }
    }
    companion object {
        var TAG = MapRouteFragment::javaClass.name
    }
}