package com.jay.viewpagerdemo2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 *  BaseFragment for keeping an instance of [ViewDataBinding]
 */
abstract class DataBindingFragment<VB : ViewDataBinding> : Fragment() {

    lateinit var vb: VB

    /**
     *  Provides layout id to be inflated
     */
    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = DataBindingUtil.inflate(layoutInflater, layoutId(), null, false)
        return vb.root
    }
}