package com.weiyung.intotheforest.reportdialog

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.api.Context
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.DialogReportBinding
import com.weiyung.intotheforest.ext.getVmFactory

class ReportDialog : AppCompatDialogFragment(){
    private val viewModel by viewModels<ReportViewModel> { getVmFactory() }
    private lateinit var binding: DialogReportBinding
//    private lateinit var viewModel: ReportViewModel
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Add2CartDialog)
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//    viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
    binding = DialogReportBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel

    binding.policeButton.setOnClickListener{
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel:110")
        startActivity(intent)
    }
    binding.ambulanceButton.setOnClickListener{
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel:119")
        startActivity(intent)
    }
    binding.smsPolice.setOnClickListener{
        val intent = Intent()
        intent.action = Intent.ACTION_SENDTO
        intent.data = Uri.parse("smsto:雙北市警察局")
        intent.putExtra("sms_body","test SOS!")
        startActivity(intent)
    }

//    val REQUSET_GPS = 444
//
//    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//    val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//    if (gps || network) {
//        getLocation()
//    } else {
//        val intent = Intent(GoogleApi.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//        startActivityForResult(intent, REQUSET_GPS)
//    }

fun getLocation() {
    val request = LocationRequest.create()
    request.apply {
        interval = 10000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 50)
    } else {
        LocationServices.getFusedLocationProviderClient(requireActivity())
            .requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .removeLocationUpdates(this)

                    if (locationResult != null && locationResult.locations.size > 0) {
                        val index = locationResult.locations.size - 1
                        val latitude = locationResult.locations[index].latitude
                        val longitude = locationResult.locations[index].longitude
                        binding.showLocation.text = "$latitude, $longitude"
                        Log.i("DATA", "latitude: $latitude, longitude: $longitude")
                    }
                }
            }, Looper.getMainLooper())
    }
}
    binding.myLocationButton.setOnClickListener{
        getLocation()
    }
    binding.sendSOSButton.setOnClickListener{
        Toast.makeText(requireContext(),R.string.sos_success,Toast.LENGTH_LONG).show()
    }
    return binding.root
    }

}