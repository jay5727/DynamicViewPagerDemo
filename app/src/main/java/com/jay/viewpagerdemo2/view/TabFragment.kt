package com.jay.viewpagerdemo2.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jay.viewpagerdemo2.R
import com.jay.viewpagerdemo2.adapter.TabPagerAdapter
import com.jay.viewpagerdemo2.base.DataBindingFragment
import com.jay.viewpagerdemo2.databinding.FragmentTabLayoutBinding
import com.jay.viewpagerdemo2.viewmodel.HomeViewModel
import com.jay.viewpagerdemo2.viewmodel.HomeViewModel.*

class TabFragment : DataBindingFragment<FragmentTabLayoutBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    private val tabAdapter: TabPagerAdapter by lazy {
        TabPagerAdapter(
            context,
            childFragmentManager,
            viewModel.fragmentList,
            viewModel.fragmentListTitle
        )
    }

    companion object {
        fun newInstance(): TabFragment {
            return TabFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_tab_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.vm = viewModel
        vb.tabAdapter = tabAdapter
        vb.lifecycleOwner = viewLifecycleOwner
        vb.tabLayout.setupWithViewPager(vb.pager);
        if (viewModel.fragmentList.isNullOrEmpty()) {
            //Load Default Fragment
            addFragmentToList(
                DisplayPurposeFragment.newInstance(getString(R.string.default_)),
                getString(R.string.default_)
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        attachObservers()
    }

    /**
     * Method to attach observers
     */
    private fun attachObservers() {
        viewModel.fragmentEvent.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FragmentChangeEvent.SetFragment -> {
                    addFragmentToList(it.fragment, it.title)
                    setupTabIcons()

                }
                is FragmentChangeEvent.GetFragment -> {
                    //TODO retrieve & set view pager's current item
                    //tabAdapter.getItem()
                }
            }
        })
    }

    /**
     * Add Delete icon except first Tab
     */
    private fun setupTabIcons() {
        for (i in 1 until vb.tabLayout.tabCount) {
            val resId = R.drawable.ic_delete_symbol_option
            vb.tabLayout.getTabAt(i)?.setIcon(resId)
        }
    }

    /**
     * @param fragment Fragment object to be added
     * @param title title of the fragment
     */
    private fun addFragmentToList(fragment: Fragment, title: String) {
        tabAdapter.addPage(fragment, title)
        tabAdapter.notifyDataSetChanged()
        //after adding every frag, let the focus move to last added item...
        vb.pager.currentItem = viewModel.fragmentList.size - 1 ?: 0
    }

    /*
    //TODO delete selected X fragment from View Pager
    private fun deleteFragmentFromList(fragment: Fragment) {
         vb.pager.currentItem = viewModel.fragmentList.indexOf(viewModel.fragmentList.last()) - 1
         Handler().postDelayed(
             { tabAdapter.deletePage(viewModel.fragmentList.indexOf(viewModel.fragmentList.last())) },
             300
         )
     }*/

}