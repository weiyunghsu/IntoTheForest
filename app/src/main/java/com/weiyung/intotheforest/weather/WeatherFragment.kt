package com.weiyung.intotheforest.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.weiyung.intotheforest.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private val webView: WebView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val webView = binding.weatherWebView
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://www.cwb.gov.tw")

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.allowFileAccess = true

        return binding.root
    }
}