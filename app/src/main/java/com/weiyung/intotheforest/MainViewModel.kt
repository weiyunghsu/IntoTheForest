package com.weiyung.intotheforest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiyung.intotheforest.util.CurrentFragmentType

class MainViewModel : ViewModel() {
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

}