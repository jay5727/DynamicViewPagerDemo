package com.jay.viewpagerdemo2.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.DialogTitle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.jay.viewpagerdemo2.R
import com.jay.viewpagerdemo2.base.DataBindingActivity
import com.jay.viewpagerdemo2.databinding.ActivityHomeBinding
import com.jay.viewpagerdemo2.extension.replaceFragment
import com.jay.viewpagerdemo2.model.EventDialog
import com.jay.viewpagerdemo2.viewmodel.HomeViewModel
import com.jay.viewpagerdemo2.viewmodel.HomeViewModel.FragmentChangeEvent.GetFragment
import com.jay.viewpagerdemo2.viewmodel.HomeViewModel.FragmentChangeEvent.SetFragment

class HomeActivity : DataBindingActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val binding: ActivityHomeBinding by binding(R.layout.activity_home)
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@HomeActivity
            vm = viewModel
        }
        binding.navView.setNavigationItemSelectedListener(this)
        setTitle()
        toggleDrawer()
        initializeDefaultFragment(savedInstanceState, 0)

    }

    /**
     * Checks if the savedInstanceState is null - onCreate() is ran
     * If so, display fragment of navigation drawer menu at position itemIndex and
     * set checked status as true
     * @param savedInstanceState
     * @param itemIndex
     */
    private fun initializeDefaultFragment(
        savedInstanceState: Bundle?,
        itemIndex: Int
    ) {
        if (savedInstanceState == null) {
            val menuItem: MenuItem =
                binding.navView.menu.getItem(itemIndex).setChecked(true)
            onNavigationItemSelected(menuItem)
        }
    }

    /**
     * Returns Event Dialog for Displaying AlertDialog
     */
    private fun addNewPageEventDialog(): EventDialog {
        return EventDialog(
            textRefTitle = getString(R.string.alert_title),
            textRefMessage = getString(R.string.alert_desc),
            textRefButtonPrimary = getString(R.string.yes),
            textRefButtonSecondary = getString(R.string.no)
        )
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_default_page -> {
                replaceFragment(TabFragment.newInstance(), R.id.framelayout_id)
                closeDrawer()
            }
            R.id.nav_add_page -> {
                showDialog(
                    eventDialog = addNewPageEventDialog(),
                    primaryLambda = { fragTitle ->
                        //Don't add tab with same name, avoiding duplicate items...
                        val alreadyExist = viewModel.fragmentListTitle.firstOrNull {
                            it.equals(fragTitle, ignoreCase = true)
                        }?.isNotEmpty() ?: false
                        if (!alreadyExist) {
                            viewModel.fragmentEvent.postValue(
                                SetFragment(
                                    DisplayPurposeFragment.newInstance(fragTitle),
                                    fragTitle
                                )
                            )
                            //add to Menu item
                            val menu: Menu = binding.navView.menu
                            menu.add(fragTitle)
                            //TODO highlight selected item in Menu...
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.existing_tab),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        closeDrawer()
                    },
                    secondaryLambda = { closeDrawer() },
                    isCancelable = false
                )
            }
            else -> {
                //TODO Load our custom added fragments
                //onClick to Display that Fragment
                val fragTitle = menuItem.title.toString()
                viewModel.fragmentEvent.postValue(
                    GetFragment(
                        fragTitle
                    )
                )
            }
        }
        return true
    }

    /**
     * Creates an instance of the ActionBarDrawerToggle class:
     * 1) Handles opening and closing the navigation drawer
     * 2) Creates a hamburger icon in the toolbar
     * 3) Attaches listener to open/close drawer on icon clicked and rotates the icon
     */
    private fun toggleDrawer() {
        val drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    /**
     * Checks if the navigation drawer is open - if so, close it
     */
    private fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    /**
     * Iterates through all the items in the navigation menu and deselects them:
     * removes the selection color
     */
    private fun deSelectCheckedState() {
        val noOfItems: Int = binding.navView.menu.size()
        for (i in 0 until noOfItems) {
            binding.navView.menu.getItem(i).isChecked = false
        }
    }

    /**
     * Method too set title on toolbar
     */
    private fun setTitle(title: String? = null) {
        binding.toolbar.title = title ?: getString(R.string.toolbar_title)
    }

}