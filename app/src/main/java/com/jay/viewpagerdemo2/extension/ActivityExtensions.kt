package com.jay.viewpagerdemo2.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * To replace fragment from activity
 */
fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    frameId: Int,
    backStackTag: String? = null
) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment)
        backStackTag?.let { addToBackStack(it) }
    }
}

/**
 * To get the fragment transaction
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

/**
 * To replace fragment from fragment
 */
fun Fragment.replaceFragment(
    context: AppCompatActivity,
    fragment: Fragment,
    frameId: Int,
    backStackTag: String? = null
) {
    context.supportFragmentManager.inTransaction {
        replace(frameId, fragment)
        backStackTag?.let { addToBackStack(it) }
    }
}
