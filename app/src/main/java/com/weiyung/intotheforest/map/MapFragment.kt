package com.weiyung.intotheforest.map

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maps.route.extensions.drawMarker
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
    private val mContext: Context? = context
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: MapViewModel
    val db = Firebase.firestore
    val latLng = hashMapOf(
        "lat" to "25.027389",
        "lng" to "121.5708249",
        "time" to 101111
    )

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
        mMap.isMyLocationEnabled = true // 右上角的定位功能
        mMap.uiSettings.isZoomControlsEnabled = true  // 右下角的放大縮小功能
        mMap.uiSettings.isCompassEnabled = true       // 左上角的指南針，要兩指旋轉才會出現
        mMap.uiSettings.isMapToolbarEnabled = true    // 右下角的導覽及開啟 Google Map功能
        // Add a marker in Sydney and move the camera
        val source = LatLng(25.027389, 121.5708249) //starting point (LatLng)
        val destination = LatLng(25.036462, 121.587468) // ending point (LatLng)
        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
            .clickable(true)
            .add(
                LatLng(25.027389, 121.5708249),
                LatLng(25.0272239, 121.5764989),
                LatLng(25.026745, 121.580568),
                LatLng(25.029370, 121.582617),
                LatLng(25.031358, 121.583596),
                LatLng(25.036462, 121.587468)))


        val appWorksSchoolPeak = LatLng(25.042477, 121.564879)
        mMap.addMarker(MarkerOptions().position(appWorksSchoolPeak).title("AppWorksSchool Peak \n台北市基隆路一段178號")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.outline_hiking_black_36)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(appWorksSchoolPeak,10F))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18F))
//        mMap.setOnPolylineClickListener(this)
//        mMap.setOnPolygonClickListener(this)

        googleMap.run {
            moveCameraOnMap(latLng = source)
            drawMarker(location = source, context = requireContext(),resDrawable = R.drawable.outline_hiking_black_36, title = "go to Google Maps to Navigate!")
            drawMarker(location = destination, context = requireContext(),resDrawable = R.drawable.outline_hiking_black_36, title = "go to Google Maps to Navigate!")
//            drawRouteOnMap(
//                getString(R.string.google_maps_key),
//                source = source,
//                destination = destination,
//                context = context!!
//            )
        }
        db.collection("latLngS")
            .add(latLng)
            .addOnSuccessListener { document ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${document.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
//        replaceFragmentSafely(MapRouteFragment())

        binding.speakButton.setOnClickListener {
            Log.i(TAG,"Where is my help button?")
            findNavController().navigate(NavigationDirections.navigateToReportDialog())
        }
    }
//    private fun replaceFragmentSafely(
//        fragment: Fragment,
//        tag: String = fragment.javaClass.name,
//        @IdRes containerViewId: Int = R.id.map
//    ) {
//        fragmentManager
//            ?.beginTransaction()
//            ?.replace(containerViewId, fragment, tag)?.commit()
//    }
}

