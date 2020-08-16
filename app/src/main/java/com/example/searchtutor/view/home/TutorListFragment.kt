package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.data.response.TutorListResponse
import com.example.searchtutor.view.adapter.TutorListAdapter
import com.example.searchtutor.view.main.MainActivity


@Suppress("DEPRECATION")
class TutorListFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mHomePresenter: HomePresenter
    private var user: PreferencesData.Users? = null

    private lateinit var mTutorListAdapter: TutorListAdapter

    private var rvTutorList: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tutor_list, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mHomePresenter = HomePresenter()
        user = PreferencesData.user(requireActivity())

        rvTutorList = root.findViewById(R.id.rvTutorList)

        val bundle = arguments
        if (bundle != null) {

            mHomePresenter.getTutorList(bundle.getString("ca_id")!!,
                object : HomePresenter.Response.TutorList {
                    override fun value(c: TutorListResponse) {
                        if (c.isSuccessful) {
                            val list = ArrayList<TutorListResponse.TutorList>()
                            list.addAll(c.data!!)
                            mTutorListAdapter = TutorListAdapter(activity!!, list) { h, b ->
                                if (b) {

                                    val bundle2 = Bundle()
                                    bundle2.putString("t_id", h["t_id"])
                                    bundle2.putString("t_name", h["t_name"])
                                    bundle2.putString("t_lname", h["t_lname"])

                                    val tutorDetailFragment: TutorDetailFragment? =
                                        requireActivity().fragmentManager
                                            .findFragmentById(R.id.fragment_tutor_detail) as TutorDetailFragment?

                                    if (tutorDetailFragment == null) {
                                        val newFragment = TutorDetailFragment()
                                        newFragment.arguments = bundle2
                                        requireFragmentManager().beginTransaction()
                                            .replace(
                                                R.id.navigation_view,
                                                newFragment,
                                                ""
                                            )
                                            .addToBackStack(null)
                                            .commit()
                                    } else {
                                        requireFragmentManager().beginTransaction()
                                            .replace(
                                                R.id.navigation_view,
                                                tutorDetailFragment,
                                                ""
                                            )
                                            .addToBackStack(null)
                                            .commit()
                                    }

                                }
                            }

                            rvTutorList!!.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = mTutorListAdapter
                                mTutorListAdapter.notifyDataSetChanged()
                            }
                        }
                    }

                    override fun error(c: String?) {

                    }
                })
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
        val bundle = arguments
        if (bundle != null) {
            tvTitle.text = bundle.getString("ca_name")
        }

        back.visibility = View.VISIBLE





        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

}