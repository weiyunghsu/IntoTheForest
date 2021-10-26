package com.weiyung.intotheforest.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentDetailBinding

class DetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModelFactory: DetailViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        detailViewModelFactory = DetailViewModelFactory(DetailFragmentArgs.fromBundle(requireArguments()).article)
        viewModel = ViewModelProvider(this, detailViewModelFactory)
            .get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val article = args.article
        binding.article = article

        binding.backButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.navigate_to_home_fragment)
        }

//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
//        mapFragment.getMapAsync(this)

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
        mMap.uiSettings.isCompassEnabled = true

    }
}