package com.weiyung.intotheforest.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentDetailBinding

class DetailFragment : Fragment(){
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModelFactory: DetailViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)
            .get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.backButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.navigate_to_home_fragment)
        }
        return binding.root
    }
}