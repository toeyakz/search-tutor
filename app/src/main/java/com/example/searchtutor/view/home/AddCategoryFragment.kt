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
import com.example.searchtutor.view.main.MainActivity


@Suppress("DEPRECATION")
class AddCategoryFragment : Fragment(), View.OnClickListener {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mHomePresenter: HomePresenter
    private var user: PreferencesData.Users? = null

    private var spinner: Spinner? = null
    private var btnSave: Button? = null

    var valueOfSpinner = ""
    var textOfSpinner = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_category, container, false)
        initView(root)
        return root
    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        spinner = root.findViewById(R.id.spinner)
        btnSave = root.findViewById(R.id.btnSave)

        btnSave!!.setOnClickListener(this)

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


                    spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        //nav.visibility = View.GONE
        tvTitle.text = "เพิ่มหมวดหมู่"
        back.visibility = View.VISIBLE



        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave -> {
                Log.d("ASas9d6a2sd", "ssssssss")
                mHomePresenter.addGroupCategory(
                    user?.t_id.toString(),
                    valueOfSpinner,
                    object : HomePresenter.Response.AddGroupCategory {
                        override fun value(c: AddGroupCategoryResponse) {
                            Toast.makeText(activity, c.message, Toast.LENGTH_SHORT).show()
                            fragmentManager!!.popBackStack()
                        }

                        override fun error(c: String?) {

                        }
                    })
            }
        }
    }


}