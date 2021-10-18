package com.weiyung.intotheforest.addarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.databinding.FragmentAddarticleBinding
import com.weiyung.intotheforest.databinding.FragmentFavoriteBinding
import com.weiyung.intotheforest.favorite.FavoriteViewModel

class AddArticleFragment : Fragment() {
    private lateinit var binding: FragmentAddarticleBinding
    private lateinit var viewModel: AddArticleViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(AddArticleViewModel::class.java)
        binding = FragmentAddarticleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}