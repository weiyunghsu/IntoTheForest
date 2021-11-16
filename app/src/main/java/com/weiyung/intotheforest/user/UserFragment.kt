package com.weiyung.intotheforest.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.databinding.FragmentMapBinding
import com.weiyung.intotheforest.databinding.FragmentUserBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.home.HomeViewModel
import com.weiyung.intotheforest.map.MapViewModel
import com.weiyung.intotheforest.util.UserManager

class UserFragment : Fragment() {
    private val viewModel by viewModels<UserViewModel> { getVmFactory() }
    private lateinit var binding: FragmentUserBinding
//    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel =
//            ViewModelProvider(this).get(UserViewModel::class.java)
        binding = FragmentUserBinding.inflate(inflater, container, false)

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