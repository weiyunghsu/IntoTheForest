package com.weiyung.intotheforest.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: MapViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val root: View = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fun setupPermission() {
            if (ContextCompat.checkSelfPermission(this.binding.root.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 999)
            } else {
                mMap.isMyLocationEnabled = true
            }
        }
        setupPermission()
//        mMap.isMyLocationEnabled = true; // 右上角的定位功能
        mMap.uiSettings.isZoomControlsEnabled = true;  // 右下角的放大縮小功能
        mMap.uiSettings.isCompassEnabled = true;       // 左上角的指南針，要兩指旋轉才會出現
        mMap.uiSettings.isMapToolbarEnabled = true;    // 右下角的導覽及開啟 Google Map功能
        // Add a marker in Sydney and move the camera
        val appWorksSchoolPeak = LatLng(25.042477, 121.564879)
        mMap.addMarker(MarkerOptions().position(appWorksSchoolPeak).title("AppWorksSchool Peak \n台北市基隆路一段178號"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(appWorksSchoolPeak,18F))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18F))
    }
}

