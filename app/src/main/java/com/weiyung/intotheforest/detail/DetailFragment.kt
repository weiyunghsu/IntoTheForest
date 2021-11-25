package com.weiyung.intotheforest.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentDetailBinding
import com.weiyung.intotheforest.ext.getVmFactory

class DetailFragment : Fragment() {
    private val viewModel by viewModels<DetailViewModel> {
        getVmFactory(
            DetailFragmentArgs.fromBundle(
                requireArguments()
            ).articleKey
        )
    }
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args = DetailFragmentArgs.fromBundle(requireArguments())

        val article = args.articleKey
        binding.article = article

        val imageAdapter = DetailAdapter()
        val imageList = article.images
        binding.detailRvImages.adapter = imageAdapter
        imageList?.let {
            imageAdapter.submitList(imageList)
        }

        binding.backButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.navigate_to_home_fragment)
        }
        binding.goToMapButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.navigate_to_map_fragment)
        }
        binding.goToWeatherButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.navigate_to_weather_fragment)
        }
        binding.detailShareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                binding.detailTitle.text.toString())
            val text = sendIntent.getStringExtra(Intent.EXTRA_TEXT)
            if (text != null) {
                if(text.isNotEmpty()){
                    binding.detailTitle.text = "$text"
                }
            }
            sendIntent.type = "text/plain"
            var shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(Intent.createChooser(sendIntent, "遊記標題"))
        }
        viewModel.favoriteAdded.observe(viewLifecycleOwner, Observer{
            it?.let{added->
                binding.detailFavoriteButton.isSelected = added
            }
        })

        return binding.root
    }


}