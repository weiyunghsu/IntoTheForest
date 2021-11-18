package com.weiyung.intotheforest.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.firebase.firestore.core.OrderBy
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maps.route.extensions.drawMarker
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import com.maps.route.model.TravelMode
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.Track
import com.weiyung.intotheforest.databinding.FragmentMapBinding
import com.weiyung.intotheforest.ext.getVmFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class RouteNumber(val positionOnSpinner: Int) {
    FOUR_ANIMALS(0),
    KUAN_IN(1)
}

class MapFragment : Fragment(), OnMapReadyCallback {
    private val viewModel by viewModels<MapViewModel> {
        getVmFactory()
//        (MapFragmentArgs.fromBundle(requireArguments()).routeKey)
    }
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var binding: FragmentMapBinding
    val db = Firebase.firestore

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

//        lifecycle.coroutineScope.launchWhenCreated {
//            val gMap = mapFragment.getMapAsync { onMapReady(it) }
//        }
        mapFragment.getMapAsync(this)

        binding.isLiveDataDesign = IntoTheForestApplication.instance.isLiveDataDesign()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getRoutesResult()
        Log.i(TAG, "Where is the Routes??? ${viewModel.routes}")
        Log.i(TAG, "Where is the _Routes??? ${viewModel._routes}")

        val viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

        viewModel._routes.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "viewModel._routes.observe, it=$it")
            it?.let {
                mapFragment.getMapAsync {
                    mMap.run {
                        Places.initialize(getApplicationContext(), R.string.google_maps_key.toString())
                        fun drawLine() {
                            coroutineScope.launch {
                                Log.i(TAG, "---------Do you draw the Route0?------")
                                drawRouteOnMap(
                                    getString(R.string.google_maps_key),
                                    source = viewModel.source,
                                    destination = viewModel.destination,
                                    context = context!!,
                                    markers = false,
                                    travelMode = TravelMode.WALKING,
                                    polygonWidth = 15
                                )
                            }
                        }
                        drawLine()
                        Log.i(TAG, "---------Do you draw the Route0 End ?-----")
                    }
                }
                binding.viewModel = viewModel
            }
        })
        binding.weatherButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToWeatherFragment())
        }
        binding.speakButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToReportDialog())
        }
        binding.mapToAddPostButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToAddArticleFragment())
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fun setupPermission() {
            if (!::mMap.isInitialized) return
            if (ContextCompat.checkSelfPermission(
                    this.binding.root.context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 999)
            }
        }
        setupPermission()
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val polyline1 = googleMap.addPolyline(
            PolylineOptions().clickable(true).color(Color.RED).addAll(viewModel.routeLine1)
        )

        val polyline2 = googleMap.addPolyline(
            PolylineOptions().clickable(true).color(Color.BLUE).addAll(viewModel.routeLine2)
        )

        mMap.addMarker(
            MarkerOptions().position(viewModel.appWorksSchoolPeak).title("AppWorksSchool Peak \n台北市基隆路一段178號")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.outline_hiking_black_36))
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viewModel.appWorksSchoolPeak, 10F))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18F))
//        mMap.setOnPolylineClickListener(this)
//        mMap.setOnPolygonClickListener(this)
        val adapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.route_list,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinnerRoutes.adapter = adapter
        val viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
        binding.spinnerRoutes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                when (pos) {
                    0 -> {
                        googleMap.run {
                            moveCameraOnMap(latLng = viewModel.source1)
                            drawMarker(
                                location = viewModel.source1, context = context!!,
                                resDrawable = R.drawable.outline_hiking_black_36,
                                title = "Go to Google Maps!"
                            )
                            drawMarker(
                                location = viewModel.destination1, context = context!!,
                                resDrawable = R.drawable.outline_sports_score_black_36,
                                title = "You do it!"
                            )
                            for (i in viewModel.view1) {
                                drawMarker(
                                    location = i, context = context!!,
                                    resDrawable = R.drawable.outline_photo_camera_black_36,
                                    title = "Great View!"
                                )
                            }
                            for (i in viewModel.wc1) {
                                drawMarker(
                                    location = i, context = context!!,
                                    resDrawable = R.drawable.outline_wc_black_36,
                                    title = "Toilet!"
                                )
                            }
                            for (i in viewModel.eat1) {
                                drawMarker(
                                    location = i, context = context!!,
                                    resDrawable = R.drawable.outline_restaurant_black_36,
                                    title = "Have something to eat!"
                                )
                            }
                        }
                        fun drawLine() {
                            coroutineScope.launch {
                                googleMap.run {
                                    Log.i(TAG, "---------Do you draw the Route1?")
                                    drawRouteOnMap(
                                        getString(R.string.google_maps_key),
                                        source = viewModel.source1,
                                        destination = viewModel.destination1,
                                        context = context!!,
                                        markers = false,
                                        travelMode = TravelMode.WALKING,
                                        polygonWidth = 15
                                    )
                                    Log.i(TAG, "---------Do you draw the Route1 End ?")
                                }
                            }
                        }
                        drawLine()
                    }
                    1 -> {
                        googleMap.run {
                            moveCameraOnMap(latLng = viewModel.source2)
                            drawMarker(
                                location = viewModel.source2, context = context!!,
                                resDrawable = R.drawable.outline_hiking_black_36,
                                title = "Go to Google Maps!"
                            )
                            drawMarker(
                                location = viewModel.destination2, context = context!!,
                                resDrawable = R.drawable.outline_sports_score_black_36,
                                title = "You do it!"
                            )
                            for (i in viewModel.view2) {
                                drawMarker(
                                    location = i, context = context!!,
                                    resDrawable = R.drawable.outline_photo_camera_black_36,
                                    title = "Great View!"
                                )
                            }
//                            for (i in viewModel.wc2) {
//                                drawMarker(
//                                    location = i, context = context!!,
//                                    resDrawable = R.drawable.outline_wc_black_36,
//                                    title = "Toilet!"
//                                )
//                            }
                            for (i in viewModel.eat2) {
                                drawMarker(
                                    location = i, context = context!!,
                                    resDrawable = R.drawable.outline_restaurant_black_36,
                                    title = "Have something to eat!"
                                )
                            }
                        }
                        fun drawLine() {
                            coroutineScope.launch {
                                googleMap.run {
                                    Log.i(TAG, "---------Do you draw the Route2?")
                                    drawRouteOnMap(
                                        getString(R.string.google_maps_key),
                                        source = viewModel.source2,
                                        destination = viewModel.destination2,
                                        context = context!!,
                                        markers = false,
                                        travelMode = TravelMode.WALKING,
                                        polygonWidth = 15
                                    )
                                    Log.i(TAG, "---------Do you draw the Route2 End ?")
                                }
                            }
                        }
                        drawLine()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
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

