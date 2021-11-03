package com.weiyung.intotheforest.map

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
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
import com.maps.route.callbacks.EstimationsCallBack
import com.maps.route.extensions.drawMarker
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.getTravelEstimations
import com.maps.route.extensions.moveCameraOnMap
import com.maps.route.model.Legs
import com.maps.route.model.TravelMode
import com.weiyung.intotheforest.MainActivity
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
//    private val mContext: Context = (activity as MainActivity).applicationContext
//    val application = requireNotNull(this.activity).application
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: MapViewModel
    val db = Firebase.firestore

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

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root

    }
    override fun onAttach(context: Context){
        super.onAttach(context)
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
        val source = LatLng(25.027389, 121.570825) //starting point (LatLng)
        val destination = LatLng(25.036462, 121.587468) // ending point (LatLng)
        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
            .clickable(true)
            .color(Color.RED)
            .add(
                LatLng(25.027389, 121.570825),
                LatLng(25.027224, 121.576499),
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

        googleMap.run{

        }
        googleMap.run {
            moveCameraOnMap(latLng = source)
            drawMarker(location = source, context = context!!,
                resDrawable = R.drawable.outline_hiking_black_36,
                title = "go to Google Maps to Navigate!")
            drawMarker(location = destination, context = context!!,
                resDrawable = R.drawable.outline_hiking_black_36,
                title = "go to Google Maps to Navigate!")
            drawRouteOnMap(
                getString(R.string.google_maps_key),
                source = source,
                destination = destination,
                context = context!!,
                markers = false,
                travelMode = TravelMode.WALKING,
                polygonWidth = 15
            )
        }

        binding.speakButton.setOnClickListener {
            Log.i(TAG,"Where is my help addPostButton?")
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
    val route : List<LatLng>? = null
}

data class City(
        val name: String? = null,
        val state: String? = null,
        val country: String? = null,
        @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
        val isCapital: Boolean? = null,
        val population: Long? = null,
        val regions: List<String>? = null
)





