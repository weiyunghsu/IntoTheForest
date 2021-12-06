package com.weiyung.intotheforest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.weiyung.intotheforest.databinding.ActivityMainBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.util.CurrentFragmentType
import com.weiyung.intotheforest.util.UserManager.isLoggedIn
import io.grpc.InternalChannelz.id

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    private lateinit var binding: ActivityMainBinding

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.myNavHostFragment)
                        .navigate(NavigationDirections.navigateToHomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    findNavController(R.id.myNavHostFragment)
                        .navigate(NavigationDirections.navigateToFavoriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_addarticle -> {
                    findNavController(R.id.myNavHostFragment).navigate(
                        NavigationDirections.navigateToAddArticleFragment()
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_map -> {
                    findNavController(R.id.myNavHostFragment)
                        .navigate(NavigationDirections.navigateToMapFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_user -> {
                    findNavController(R.id.myNavHostFragment)
                        .navigate(NavigationDirections.navigateToUserFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.currentFragmentType.observe(
            this,
            Observer {
                it?.let {
                    when {
                        (it != CurrentFragmentType.LOGIN && !isLoggedIn)
                        -> {
                            Log.i(TAG, "[1 ${viewModel.currentFragmentType.value}]")
                            findNavController(R.id.myNavHostFragment)
                                .navigate(NavigationDirections.navigateToLoginFragment())
                        }
                    }
                }
                Log.i(TAG, "[3 ${viewModel.currentFragmentType.value}]")
            }
        )

        val navController = findNavController(R.id.myNavHostFragment)
        val navView: BottomNavigationView = binding.bottomNavView
        NavigationUI.setupWithNavController(navView, navController)
        setupBottomNav()

        setupNavController()
    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(2) as BottomNavigationItemView
    }

    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener {
            navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> {
                    binding.bottomNavView.menu.getItem(BOTTOM_HOME_POS).isChecked = true
                    CurrentFragmentType.HOME
                }
                R.id.favoriteFragment -> CurrentFragmentType.FAVORITE
                R.id.addArticleFragment -> CurrentFragmentType.ADDARTICLE
                R.id.mapFragment -> CurrentFragmentType.MAP
                R.id.userFragment -> {
                    binding.bottomNavView.menu.getItem(BOTTOM_USER_POS).isChecked = true
                    CurrentFragmentType.USER
                }
                R.id.loginFragment -> CurrentFragmentType.LOGIN
                R.id.detailFragment -> CurrentFragmentType.DETAIL
                R.id.weatherFragment -> CurrentFragmentType.WEATHER
                R.id.reportDialog -> CurrentFragmentType.REPORT
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    companion object {
        private const val BOTTOM_HOME_POS = 0
        private const val BOTTOM_USER_POS = 4
    }
}
