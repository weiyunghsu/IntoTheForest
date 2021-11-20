package com.weiyung.intotheforest.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.weiyung.intotheforest.databinding.FragmentFavoriteBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.util.UserManager

class FavoriteFragment : Fragment() {
    private val viewModel by viewModels<FavoriteViewModel> { getVmFactory() }
    private lateinit var binding: FragmentFavoriteBinding
//    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.user = UserManager.addUserInfo()

        binding.recyclerFavorite.adapter = FavoriteAdapter(viewModel)
        binding.recyclerFavorite.layoutManager = LinearLayoutManager(context)
        binding.recyclerFavorite.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

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

        viewModel.articleList.observe(viewLifecycleOwner) {
            it?.let {
                binding.viewModel = viewModel
            }
        }

//        viewModel.favorite.observe(viewLifecycleOwner, Observer {
//            Log.i("i-observe","$it")
//            viewModel.transform()
//        })

        return binding.root
    }
}