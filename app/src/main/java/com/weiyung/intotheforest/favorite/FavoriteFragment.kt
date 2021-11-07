package com.weiyung.intotheforest.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.weiyung.intotheforest.databinding.FragmentFavoriteBinding
import com.weiyung.intotheforest.ext.getVmFactory

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
//    private val viewModel by viewModels<FavoriteViewModel> { getVmFactory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.recyclerFavorite.adapter = FavoriteAdapter(viewModel)

        binding.layoutSwipeRefreshFavorite.setOnRefreshListener {
            binding.recyclerFavorite.adapter?.notifyDataSetChanged()
            binding.layoutSwipeRefreshFavorite.isRefreshing = false
        }
        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(FavoriteFragmentDirections.navigateToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )

//        viewModel.favorite.observe(viewLifecycleOwner, Observer {
//            Log.i("i-observe","$it")
//            viewModel.transform()
//        })

        return binding.root
    }
}