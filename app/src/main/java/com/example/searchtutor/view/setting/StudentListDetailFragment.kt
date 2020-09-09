package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.data.model.User
import com.example.searchtutor.data.response.StudentResponse
import com.example.searchtutor.view.adapter.StudentAllAdapter
import com.example.searchtutor.view.main.MainActivity
import java.util.ArrayList


@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StudentListDetailFragment : Fragment() {

    private lateinit var mSettingPresenter: SettingPresenter
    private lateinit var mStudentAllAdapter: StudentAllAdapter

    private lateinit var rvStudentList: RecyclerView

    override fun onResume() {
        super.onResume()
        manageToolbar()
        setStudentList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_student_list_deatail, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        mSettingPresenter = SettingPresenter()

        rvStudentList = root.findViewById(R.id.rvStudentList)

    }

    private fun setStudentList() {
        mSettingPresenter.getAllStudent(object : SettingPresenter.Response.Student {
            override fun value(c: StudentResponse) {
                if (c.isSuccessful) {
                    if (c.data!!.isNotEmpty()) {
                        mStudentAllAdapter =
                            StudentAllAdapter(activity!!, c.data as ArrayList<User>) { h, b ->
                                if (b) {
                                    val bundle = Bundle()
                                    bundle.putString("st_id", h["st_id"].toString())
                                    bundle.putString("type", "Student")

                                    val detail: ProfileEditFragment? =
                                        activity!!.fragmentManager
                                            .findFragmentById(R.id.fragment_profile_edit) as ProfileEditFragment?

                                    if (detail == null) {
                                        val newFragment = ProfileEditFragment()
                                        newFragment.arguments = bundle
                                        fragmentManager!!.beginTransaction()
                                            .replace(R.id.navigation_view, newFragment, "")
                                            .addToBackStack(null)
                                            .commit()
                                    } else {
                                        fragmentManager!!.beginTransaction()
                                            .replace(R.id.navigation_view, detail, "")
                                            .addToBackStack(null)
                                            .commit()
                                    }
                                }
                            }

                        rvStudentList.apply {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = mStudentAllAdapter
                            mStudentAllAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun error(c: String?) {

            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {
        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()

        tvTitle.text = "ข้อมูลนักเรียน"


        back.visibility = View.VISIBLE

        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }
}