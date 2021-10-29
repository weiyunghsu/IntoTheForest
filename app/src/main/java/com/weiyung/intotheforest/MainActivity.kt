package com.weiyung.intotheforest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.weiyung.intotheforest.databinding.ActivityMainBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.map.MapRouteFragment
import com.weiyung.intotheforest.util.CurrentFragmentType

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel> { getVmFactory() }

//    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToHomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToFavoriteFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_addarticle -> {
                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToAddArticleFragment(
                    viewModel.user.value!!
                ))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToMapFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_user -> {
                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToUserFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.currentFragmentType.observe(
            this,
            Observer {
                Log.i("Wei","[${viewModel.currentFragmentType.value}]")
//                Logger.i("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
//                Logger.i("[${viewModel.currentFragmentType.value}]")
//                Logger.i("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
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
//        val bindingBadge = BadgeBottomBinding.inflate(LayoutInflater.from(this), itemView, true)
//        bindingBadge.lifecycleOwner = this
//        bindingBadge.viewModel = viewModel
    }
    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> CurrentFragmentType.HOME
                R.id.favoriteFragment -> CurrentFragmentType.FAVORITE
                R.id.addArticleFragment -> CurrentFragmentType.ADDARTICLE
                R.id.mapFragment -> CurrentFragmentType.MAP
                R.id.userFragment -> CurrentFragmentType.USER
                else -> viewModel.currentFragmentType.value
            }
        }
    }


}