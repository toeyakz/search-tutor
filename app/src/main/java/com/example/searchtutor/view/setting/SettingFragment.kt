package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomProgressDialog
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.view.login.LoginActivity
import com.example.searchtutor.view.main.MainActivity


@Suppress("DEPRECATION")
class SettingFragment : Fragment(), View.OnClickListener {


    private lateinit var mSettingPresenter: SettingPresenter
    private var dialog: CustomProgressDialog? = null
    private var user: PreferencesData.Users? = null

    override fun onResume() {
        super.onResume()
        manageToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        user = PreferencesData.user(requireActivity())
        mSettingPresenter = SettingPresenter()
        val btnLogout: LinearLayout = root.findViewById(R.id.btnLogout)
        val btnCommentList: LinearLayout = root.findViewById(R.id.btnCommentList)
        val btnProfile: LinearLayout = root.findViewById(R.id.btnProfile)
        val btnListTutor: LinearLayout = root.findViewById(R.id.btnListTutor)
        val btnListStudent: LinearLayout = root.findViewById(R.id.btnListStudent)

        when (user?.type) {
            "admin" -> {
                btnProfile.visibility = View.GONE
                btnListTutor.visibility = View.VISIBLE
                btnListStudent.visibility = View.VISIBLE
                btnCommentList.visibility = View.GONE

            }
            "tutor" -> {
                btnCommentList.visibility = View.GONE
                btnListTutor.visibility = View.GONE
                btnListStudent.visibility = View.GONE
            }
            else -> {
                btnCommentList.visibility = View.GONE
                btnListTutor.visibility = View.GONE
                btnListStudent.visibility = View.GONE
            }
        }

        btnLogout.setOnClickListener(this)
        btnCommentList.setOnClickListener(this)
        btnProfile.setOnClickListener(this)
        btnListTutor.setOnClickListener(this)
        btnListStudent.setOnClickListener(this)
        return root
    }


    @SuppressLint("SetTextI18n")
    private fun manageToolbar() {

        val tvTitle = (activity as MainActivity).getTvTitle()
        val back = (activity as MainActivity).getBack()
        val nav = (activity as MainActivity).getNav()
        tvTitle.text = "ตั้งค่า"
        back.visibility = View.GONE

    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnListTutor -> {

                val tutorListDetailFragment: TutorListDetailFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_tutor_list_detail) as TutorListDetailFragment?

                if (tutorListDetailFragment == null) {
                    val newFragment = TutorListDetailFragment()
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
                            tutorListDetailFragment,
                            ""
                        )
                        .addToBackStack(null)
                        .commit()
                }

            }

            R.id.btnListStudent -> {
                val studentListDetailFragment: StudentListDetailFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_student_list_detail) as StudentListDetailFragment?

                if (studentListDetailFragment == null) {
                    val newFragment = StudentListDetailFragment()
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
                            studentListDetailFragment,
                            ""
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }


            R.id.btnLogout -> {
                dialog = CustomProgressDialog(activity!!, "กำลังโหลด..")
                dialog?.show()
                mSettingPresenter.logout(activity!!) {
                    if (it) {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                        val intent = Intent(activity, LoginActivity::class.java)
                        activity?.startActivity(intent)
                        activity?.finish()
                    } else {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                    }
                }
            }
            R.id.btnCommentList -> {

                val commentListFragment: CommentListFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_comment_list) as CommentListFragment?

                if (commentListFragment == null) {
                    val newFragment = CommentListFragment()
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
                            commentListFragment,
                            ""
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
            R.id.btnProfile -> {
                val profileFragment: ProfileFragment? =
                    requireActivity().fragmentManager
                        .findFragmentById(R.id.fragment_profile) as ProfileFragment?

                if (profileFragment == null) {
                    val newFragment = ProfileFragment()

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
                            profileFragment,
                            ""
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }


}