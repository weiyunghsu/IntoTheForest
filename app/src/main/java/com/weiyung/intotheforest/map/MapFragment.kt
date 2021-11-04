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
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
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
import com.maps.route.model.TravelMode
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentMapBinding
import com.weiyung.intotheforest.detail.DetailFragmentArgs
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.factory.MapViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {
    private val viewModel by viewModels<MapViewModel> {
        getVmFactory()
//        (MapFragmentArgs.fromBundle(requireArguments()).routeKey)
    }
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var binding: FragmentMapBinding
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        lifecycle.coroutineScope.launchWhenCreated {
            val gMap = mapFragment.getMapAsync { onMapReady(it) }
        }
//        mapFragment.getMapAsync(this)

        binding.isLiveDataDesign = IntoTheForestApplication.instance.isLiveDataDesign()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getRoutesResult()
        Log.i(TAG, "Where is the Routes??? ${viewModel.routes}")
        Log.i(TAG, "Where is the _Routes??? ${viewModel._routes}")

        val source = LatLng(25.027389, 121.570825) //starting point (LatLng)
        val destination = LatLng(25.036462, 121.587468) // ending point (LatLng)

        viewModel._routes.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "viewModel._routes.observe, it=$it")
            it?.let {
                mapFragment.getMapAsync {
                    mMap.run {
                    Log.i(TAG, "---------Do you draw the Route?------")
                    drawRouteOnMap(
                        getString(R.string.google_maps_key),
                        source = source,
                        destination = destination,
                        context = context!!,
                        markers = false,
                        travelMode = TravelMode.WALKING,
                        polygonWidth = 15
                    )
                    Log.i(TAG, "---------Do you draw the Route End ?-----")
                }
                }
                binding.viewModel = viewModel
            }
        })

        binding.speakButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToReportDialog())
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        fun setupPermission() {
            if (ContextCompat.checkSelfPermission(
                    this.binding.root.context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
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
                    LatLng(25.036462, 121.587468)
                )
        )

        val appWorksSchoolPeak = LatLng(25.042477, 121.564879)
        mMap.addMarker(
            MarkerOptions().position(appWorksSchoolPeak).title("AppWorksSchool Peak \n台北市基隆路一段178號")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.outline_hiking_black_36))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(appWorksSchoolPeak, 10F))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18F))
//        mMap.setOnPolylineClickListener(this)
//        mMap.setOnPolygonClickListener(this)

        googleMap.run {
            moveCameraOnMap(latLng = source)
            drawMarker(
                location = source, context = context!!,
                resDrawable = R.drawable.outline_hiking_black_36,
                title = "go to Google Maps to Navigate!"
            )
            drawMarker(
                location = destination, context = context!!,
                resDrawable = R.drawable.outline_hiking_black_36,
                title = "go to Google Maps to Navigate!"
            )
        }


            googleMap.run {
                Log.i(TAG, "---------Do you draw the Route?")
                drawRouteOnMap(
                    getString(R.string.google_maps_key),
                    source = source,
                    destination = destination,
                    context = context!!,
                    markers = false,
                    travelMode = TravelMode.WALKING,
                    polygonWidth = 15
                )
                Log.i(TAG, "---------Do you draw the Route End ?")
            }
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

