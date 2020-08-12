package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.view.main.MainActivity

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private var user: PreferencesData.Users? = null
    private lateinit var mHomePresenter: HomePresenter

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_home, container, false)
        initView(root)
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        tvTitle.text = "หน้าแรก"
        back.visibility = View.GONE
        (activity as MainActivity?)!!.updateNavigationBarState(R.id.navigation_home)

    }


    private fun initView(root: View) {
        mHomePresenter = HomePresenter()
        supportFragmentManager = fragmentManager

    }


}