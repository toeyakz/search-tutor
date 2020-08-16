package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.controler.Utils
import com.example.searchtutor.data.response.StudentResponse
import com.example.searchtutor.data.response.TutorResponse
import com.example.searchtutor.view.home.HomePresenter
import com.example.searchtutor.view.main.MainActivity
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mSettingPresenter: SettingPresenter
    private var user: PreferencesData.Users? = null

    private var btnEditProfile: Button? = null
    private var tvType: TextView? = null
    private var tvName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvAddress: TextView? = null
    private var imgProfile: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mSettingPresenter = SettingPresenter()
        user = PreferencesData.user(requireActivity())

        btnEditProfile = root.findViewById(R.id.btnEditProfile)
        tvType = root.findViewById(R.id.tvType)
        tvName = root.findViewById(R.id.tvName)
        tvEmail = root.findViewById(R.id.tvEmail)
        tvAddress = root.findViewById(R.id.tvAddress)
        imgProfile = root.findViewById(R.id.imgProfile)

        btnEditProfile!!.setOnClickListener {
            val profileEditFragment: ProfileEditFragment? =
                requireActivity().fragmentManager
                    .findFragmentById(R.id.fragment_profile_edit) as ProfileEditFragment?

            if (profileEditFragment == null) {
                val newFragment = ProfileEditFragment()
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
                        profileEditFragment,
                        ""
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }


        setDate()

    }

    private fun setDate() {
        if (user?.type == "tutor") {
            mSettingPresenter.getTutor(
                user?.t_id.toString(),
                object : SettingPresenter.Response.Tutor {
                    @SuppressLint("SetTextI18n")
                    override fun value(c: TutorResponse) {
                        if (c.isSuccessful) {
                            if(c.data!![0].t_img != ""){
                                Picasso.get().load(Utils.host + "search_tutor/img_profile/" + c.data[0].t_img)
                                    .into(imgProfile)
                            }else{
                                Picasso.get().load(R.drawable.feature_theguest_mario)
                                    .into(imgProfile)
                            }

                            tvType!!.text = user?.type
                            tvName!!.text =
                                "ชื่อ-นามสกุล : " + c.data[0].t_name + " " + c.data[0].t_lname
                            tvEmail!!.text = "อีเมล : " + c.data[0].t_email
                            tvAddress!!.text = "ที่อยู่ : " + c.data[0].t_address
                        }
                    }

                    override fun error(c: String?) {

                    }
                })
        } else {
            mSettingPresenter.getStudent(
                user?.st_id.toString(),
                object : SettingPresenter.Response.Student {
                    @SuppressLint("SetTextI18n")
                    override fun value(c: StudentResponse) {
                        if (c.isSuccessful) {
                            if(c.data!![0].st_img != ""){
                                Picasso.get().load(Utils.host + "search_tutor/img_profile/" + c.data[0].st_img)
                                    .into(imgProfile)
                            }else{
                                Picasso.get().load(R.drawable.feature_theguest_mario)
                                    .into(imgProfile)
                            }

                            tvType!!.text = "นักเรียน"
                            tvName!!.text =
                                "ชื่อ-นามสกุล : " + c.data[0].st_name + " " + c.data[0].st_lname
                            tvEmail!!.text = "อีเมล : " + c.data[0].st_email
                            tvAddress!!.text = "ที่อยู่ : " + c.data[0].st_address
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

        tvTitle.text = "ข้อมูลส่วนตัว"


        back.visibility = View.VISIBLE


        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}