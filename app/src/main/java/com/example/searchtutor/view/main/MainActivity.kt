package com.example.searchtutor.view.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.searchtutor.R
import com.example.searchtutor.controler.Constants.Companion.HOME
import com.example.searchtutor.controler.Constants.Companion.SETTING
import com.example.searchtutor.view.home.HomeFragment
import com.example.searchtutor.view.login.RegisterActivity
import com.example.searchtutor.view.setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.util.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val SELECTED_ITEM = "arg_selected_item"
    private var mHomeFragment: HomeFragment? = null
    private var mSettingFragment: SettingFragment? = null

    private var navView: BottomNavigationView? = null
    private var mSelectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mHomeFragment = HomeFragment()
        mSettingFragment = SettingFragment()


        navView = findViewById(R.id.nav_view)
        navView!!.setOnNavigationItemSelectedListener(this)
        navView!!.selectedItemId = R.id.navigation_home

    }

    fun updateNavigationBarState(actionId: Int) {
        val menu: Menu = nav_view.menu
        var i = 0
        val size = menu.size()
        while (i < size) {
            val item = menu.getItem(i)
            if (item.itemId == actionId) {
                item.isChecked = true
            }
            i++
        }
    }

    fun getTvTitle(): TextView {
        return text_toolbar
    }

    fun getBack(): ImageView {
        return back
    }

    fun getNav(): BottomNavigationView {
        return nav_view
    }

    @SuppressLint("SetTextI18n")
    private fun setToolbar() {
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayShowCustomEnabled(false)
    }

    override fun onBackPressed() {

        Log.d("asd5asda", supportFragmentManager.backStackEntryCount.toString())

        val isFragmentPopped = handleNestedFragmentBackStack(supportFragmentManager)


        if (!isFragmentPopped && supportFragmentManager.backStackEntryCount == 2) {
            // navView!!.selectedItemId = R.id.navigation_home
        } else if (!isFragmentPopped && supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else if (!isFragmentPopped) {
            super.onBackPressed()
        }
    }

    private fun handleNestedFragmentBackStack(fragmentManager: FragmentManager): Boolean {
        val childFragmentList = fragmentManager.fragments

        if (childFragmentList.size > 0) {
            for (index in childFragmentList.size - 1 downTo 0) {
                val fragment = childFragmentList[index]
                val isPopped = handleNestedFragmentBackStack(fragment.childFragmentManager)
                return when {
                    isPopped -> true
                    fragmentManager.backStackEntryCount > 1 -> {

                        fragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {

                val fm = supportFragmentManager
                for (entry in 0 until fm.backStackEntryCount) {
                    if (entry > 0) {
                        fm.popBackStack()
                    }
                }

                mSelectedItem = 99
                if (mHomeFragment == null) {
                    val newFragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, newFragment, HOME)
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, mHomeFragment!!, HOME)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }

            R.id.navigation_setting -> {
                //   viewFragment(ProfileFragment(), "FRAGMENT_OTHER")
                mSelectedItem = 0
                //  selectFragment(profileFragment!!)
                if (mSettingFragment == null) {
                    val newFragment = SettingFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, newFragment, SETTING)
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_view, mSettingFragment!!, SETTING)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }
        }
        return false
    }
}