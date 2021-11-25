package com.weiyung.intotheforest.home

import android.content.ContentValues.TAG
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weiyung.intotheforest.IntoTheForestApplication
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.databinding.FragmentHomeBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.util.UserManager

class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.isLiveDataDesign = IntoTheForestApplication.instance.isLiveDataDesign()
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        val adapter = HomeAdapter(HomeAdapter.OnClickListener {
            viewModel.displayDetail(it)
        })

        binding.rvHome.adapter = adapter

        viewModel.naviToSelectedArticle.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
                viewModel.displayDetailAll()
            }
        })
        val listener = SwipeRefreshLayout.OnRefreshListener {
            binding.rvHome.adapter = adapter
            adapter.notifyDataSetChanged()
            binding.swipe.isRefreshing = false
        }
        binding.swipe.setOnRefreshListener(listener)

        viewModel.liveArticles.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"viewModel.liveArticles.observe, it=$it")
            it?.let {
                binding.viewModel = viewModel
            }
        })

        return binding.root
    }
}