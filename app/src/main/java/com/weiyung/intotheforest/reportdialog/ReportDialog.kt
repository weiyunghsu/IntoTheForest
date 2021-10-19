package com.weiyung.intotheforest.reportdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.databinding.DialogReportBinding
import com.weiyung.intotheforest.map.MapViewModel

class ReportDialog : AppCompatDialogFragment(){
    private lateinit var binding: DialogReportBinding
    private lateinit var viewModel: ReportViewModel
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Add2CartDialog)
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    viewModel =
        ViewModelProvider(this).get(ReportViewModel::class.java)
    binding = DialogReportBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    return binding.root
    }

}