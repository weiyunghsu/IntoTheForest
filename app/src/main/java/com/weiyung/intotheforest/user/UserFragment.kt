package com.weiyung.intotheforest.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.databinding.FragmentMapBinding
import com.weiyung.intotheforest.databinding.FragmentUserBinding
import com.weiyung.intotheforest.map.MapViewModel

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)
        binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.btMyFavorites.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToFavoriteFragment())
        }
        binding.lottieMountainBirds.repeatCount = -1
        binding.lottieMountainBirds.playAnimation()
        return binding.root
    }
}