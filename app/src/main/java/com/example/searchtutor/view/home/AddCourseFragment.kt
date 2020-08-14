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
import com.example.searchtutor.data.response.AddCourseResponse
import com.example.searchtutor.view.main.MainActivity
import com.google.android.material.textfield.TextInputEditText


@Suppress("DEPRECATION")
class AddCourseFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mHomePresenter: HomePresenter
    private var user: PreferencesData.Users? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_course, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        val btnSaveCourse: Button = root.findViewById(R.id.btnSaveCourse)
        val edtCourseName: TextInputEditText = root.findViewById(R.id.edtCourseName)
        val edtCourseDetail: TextInputEditText = root.findViewById(R.id.edtCourseDetail)
        val edtCoursePrice: TextInputEditText = root.findViewById(R.id.edtCoursePrice)



        btnSaveCourse.setOnClickListener {
            val bundle = arguments
            if (bundle != null) {

                mHomePresenter.addCourse(
                    user?.t_id.toString(),
                    bundle.getString("g_id")!!,
                    edtCourseName.text.toString(),
                    edtCourseDetail.text.toString(),
                    edtCoursePrice.text.toString(),
                    object : HomePresenter.Response.AddCourse {
                        override fun value(c: AddCourseResponse) {
                            Toast.makeText(activity, c.message, Toast.LENGTH_SHORT).show()
                            fragmentManager!!.popBackStack()
                        }

                        override fun error(c: String?) {

                        }
                    }
                )
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
        tvTitle.text = "เพิ่มคอร์ส"
        back.visibility = View.VISIBLE





        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}