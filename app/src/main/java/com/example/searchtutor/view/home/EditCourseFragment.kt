package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.view.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.HashMap


@Suppress("DEPRECATION")
class EditCourseFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mHomePresenter: HomePresenter
    private var user: PreferencesData.Users? = null

    private var edtCourseName: TextInputEditText? = null
    private var edtCourseDetail: TextInputEditText? = null
    private var edtCoursePrice: TextInputEditText? = null

    private var btnSaveCourse: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_edit_course, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        edtCourseName = root.findViewById(R.id.edtCourseName)
        edtCourseDetail = root.findViewById(R.id.edtCourseDetail)
        edtCoursePrice = root.findViewById(R.id.edtCoursePrice)
        btnSaveCourse = root.findViewById(R.id.btnSaveCourse)

        val bundle = arguments
        if (bundle != null) {

            edtCourseName!!.setText(bundle.getString("gc_name")!!)
            edtCourseDetail!!.setText(bundle.getString("gc_detail")!!)
            edtCoursePrice!!.setText(bundle.getString("gc_price")!!)

            btnSaveCourse!!.setOnClickListener {

                val myMap2 = HashMap<String, String>()
                myMap2["gc_id"] = bundle.getString("gc_id").toString()
                myMap2["gc_name"] = edtCourseName!!.text.toString()
                myMap2["gc_detail"] = edtCourseDetail!!.text.toString()
                myMap2["gc_price"] = edtCoursePrice!!.text.toString()

                mHomePresenter.updateCourse(
                    bundle.getString("gc_id")!!,
                    myMap2
                ) { b, s ->
                    if (b) {
                        Toast.makeText(
                            activity,
                            s,
                            Toast.LENGTH_SHORT
                        ).show()
                        requireFragmentManager().popBackStack()
                    } else {

                        Toast.makeText(
                            activity,
                            s,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }


    }

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        //nav.visibility = View.GONE
        tvTitle.text = "แก้่ไขคอร์ส"
        back.visibility = View.VISIBLE



        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}