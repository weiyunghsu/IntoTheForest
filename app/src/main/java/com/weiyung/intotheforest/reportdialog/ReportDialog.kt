package com.weiyung.intotheforest.reportdialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.databinding.DialogReportBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.login.LoginViewModel
import com.weiyung.intotheforest.map.MapViewModel

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
//    viewModel =
//        ViewModelProvider(this).get(ReportViewModel::class.java)
    binding = DialogReportBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel

    binding.policeButton.setOnClickListener{
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel:110")
        startActivity(intent)
    }
    binding.rescueButton.setOnClickListener{
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


    return binding.root
    }

}