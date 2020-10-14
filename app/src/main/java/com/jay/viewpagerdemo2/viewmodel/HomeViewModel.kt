package com.jay.viewpagerdemo2.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.jay.viewpagerdemo2.base.SingleLiveEvent

class HomeViewModel : ViewModel() {

    val fragmentEvent = SingleLiveEvent<FragmentChangeEvent>()

    var fragmentList: ArrayList<Fragment> = ArrayList()
    var fragmentListTitle: ArrayList<String> = ArrayList()

    sealed class FragmentChangeEvent {
        data class SetFragment(val fragment: Fragment, val title: String = "") : FragmentChangeEvent()
        data class GetFragment(val title: String = "") : FragmentChangeEvent()
    }
}