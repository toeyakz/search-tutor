package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.data.model.User
import com.example.searchtutor.data.response.TutorResponse
import com.example.searchtutor.view.adapter.StudentAllAdapter
import com.example.searchtutor.view.adapter.TutorAllAdapter
import com.example.searchtutor.view.main.MainActivity
import java.util.ArrayList


class TutorListDetailFragment : Fragment() {

    private lateinit var mSettingPresenter: SettingPresenter
    private lateinit var mTutorAllAdapter: TutorAllAdapter

    private lateinit var rvTutor: RecyclerView

    override fun onResume() {
        super.onResume()
        manageToolbar()
        setTutorList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tutor_list_detail, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        mSettingPresenter = SettingPresenter()

        rvTutor = root.findViewById(R.id.rvTutor)

    }

    private fun setTutorList() {
        mSettingPresenter.getAllTutor(object : SettingPresenter.Response.Tutor {
            override fun value(c: TutorResponse) {
                if (c.isSuccessful) {
                    if (c.data!!.isNotEmpty()) {
                        mTutorAllAdapter =
                            TutorAllAdapter(activity!!, c.data as ArrayList<User>) { h, b ->
                                if (b) {
                                    val bundle = Bundle()
                                    bundle.putString("t_id", h["t_id"].toString())
                                    bundle.putString("type", "tutor")

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
                        rvTutor.apply {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = mTutorAllAdapter
                            mTutorAllAdapter.notifyDataSetChanged()
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

        tvTitle.text = "ข้อมูลติวเตอร์"


        back.visibility = View.VISIBLE

        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}