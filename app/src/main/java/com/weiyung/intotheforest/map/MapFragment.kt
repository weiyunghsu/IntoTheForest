package com.weiyung.intotheforest.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.addarticle.AddArticleViewModel
import com.weiyung.intotheforest.databinding.FragmentAddarticleBinding
import com.weiyung.intotheforest.databinding.FragmentMapBinding

class MapFragment : Fragment() {
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
        val root: View = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}