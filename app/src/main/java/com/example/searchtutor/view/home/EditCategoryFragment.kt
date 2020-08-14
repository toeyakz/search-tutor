package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.data.response.AddGroupCategoryResponse
import com.example.searchtutor.data.response.CategoryResponse
import com.example.searchtutor.data.response.UpdateGroupCategoryResponse
import com.example.searchtutor.view.main.MainActivity


@Suppress("DEPRECATION")
class EditCategoryFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mHomePresenter: HomePresenter
    private var user: PreferencesData.Users? = null

    private var spinner: Spinner? = null
    private var btnSav2: Button? = null
    private var btnDelete: TextView? = null

    var valueOfSpinner = ""
    var textOfSpinner = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_edit_category, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        spinner = root.findViewById(R.id.spinner2)
        btnSav2 = root.findViewById(R.id.btnSave2)
        btnDelete = root.findViewById(R.id.btnDelete)

        btnSav2!!.setOnClickListener(this)
        btnDelete!!.setOnClickListener(this)

        val bundle = arguments
        if (bundle != null) {

            mHomePresenter.getCategory(object : HomePresenter.Response.Category {
                override fun value(c: CategoryResponse) {
                    if (c.isSuccessful) {

                        val list = ArrayList<String>()
                        for (i in c.data!!) {
                            list.add(i.ca_name!!)
                        }
                        val adapter =
                            ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, list)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner?.adapter = adapter

                        if (!bundle.getString("ca_name").equals("")) {
                            val spinnerPosition = adapter.getPosition(bundle.getString("ca_name"))
                            spinner!!.setSelection(spinnerPosition + 1)

                            for (ii in c.data) {
                                if (bundle.getString("ca_name") == ii.ca_name) {
                                    valueOfSpinner = ii.ca_id.toString()
                                }
                            }

                        }


                        spinner!!.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>,
                                    view: View,
                                    i: Int,
                                    l: Long
                                ) {
                                    textOfSpinner = if (i != -1) {
                                        adapterView.getItemAtPosition(i).toString()
                                    } else {
                                        ""
                                    }

                                    for (ii in c.data) {
                                        if (textOfSpinner == ii.ca_name) {
                                            valueOfSpinner = ii.ca_id.toString()
                                        }
                                    }

                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                            }


                    }
                }

                override fun error(c: String?) {
                    TODO("Not yet implemented")
                }
            })
        }


    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave2 -> {
                val bundle = arguments
                if (bundle != null) {
                    mHomePresenter.updateGroupCategory(
                        bundle.getString("g_id")!!,
                        user?.t_id.toString(),
                        valueOfSpinner,
                        object : HomePresenter.Response.UpdateGroupCategory {
                            override fun value(c: UpdateGroupCategoryResponse) {
                                Toast.makeText(activity, c.message, Toast.LENGTH_SHORT).show()
                                fragmentManager!!.popBackStack()
                            }

                            override fun error(c: String?) {

                            }
                        })
                }
            }
            R.id.btnDelete -> {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        //nav.visibility = View.GONE
        tvTitle.text = "แก้่ไขหมวดหมู่"
        back.visibility = View.VISIBLE



        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}