package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomProgressDialog
import com.example.searchtutor.view.login.LoginActivity


class SettingFragment : Fragment(), View.OnClickListener {


    private lateinit var mSettingPresenter: SettingPresenter
    private var dialog: CustomProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_setting, container, false)

        mSettingPresenter = SettingPresenter()
        val btnLogout: LinearLayout = root.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener(this)
        return root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(v: View) {
        when (v.id) {
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
        }
    }


}