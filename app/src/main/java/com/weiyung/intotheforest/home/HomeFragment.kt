package com.weiyung.intotheforest.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = HomeAdapter(HomeAdapter.OnClickListener{
            viewModel.displayDetail(it)
        })
        binding.rvHome.adapter = adapter
        val mockList = viewModel.list
        adapter.submitList(mockList)



        viewModel.naviToSelectedArticle.observe(viewLifecycleOwner, Observer {
            if (null != it){
                this.findNavController().navigate(NavigationDirections.navigateToDetailFragment())
                viewModel.displayDetailAll()
            }
        })
        val listener = SwipeRefreshLayout.OnRefreshListener {
            binding.rvHome.adapter = adapter
            adapter.notifyDataSetChanged()
            binding.swipe.isRefreshing = false
        }
        binding.swipe.setOnRefreshListener(listener)

        return binding.root
    }
}