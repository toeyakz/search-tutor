package com.example.searchtutor.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomProgressDialog
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.data.model.User
import com.example.searchtutor.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginPresenter: LoginPresenter
    private var dialog: CustomProgressDialog? = null

    private var user: PreferencesData.Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mLoginPresenter = LoginPresenter()
        onclick()


    }


    private fun onclick() {

        tvRegister.setOnClickListener {
            val myIntent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(myIntent)
        }

        btnLogin.setOnClickListener {
            dialog = CustomProgressDialog(this, "กำลังโหลด..")
            val ad: AlertDialog.Builder = AlertDialog.Builder(this)
            ad.setTitle("พบข้อมผิดพลาด!")
            ad.setPositiveButton("ปิด", null)
            dialog?.show()

            if (edtUsername.text?.isEmpty()!!) {
                if (dialog?.isShowing!!) {
                    dialog?.dismiss()
                }
                ad.setMessage("กรุณากรอกชื่อผู้ใช้")
                ad.show()
                edtUsername.requestFocus()
                return@setOnClickListener
            }

            if (edtPassword.text?.isEmpty()!!) {
                if (dialog?.isShowing!!) {
                    dialog?.dismiss()
                }
                ad.setMessage("กรุณากรอกรหัสผ่าน")
                ad.show()
                edtPassword.requestFocus()
                return@setOnClickListener
            }
            mLoginPresenter.callLogin(
                this,
                edtUsername.text.toString(),
                edtPassword.text.toString(), object : LoginPresenter.View {
                    override fun onSuccessService(user: List<User>?, type: String) {
                        when (type) {
                            "user" -> {
                                mLoginPresenter.addDataUserToPrefs(applicationContext, user, type)
                                    val myIntent = Intent(applicationContext, MainActivity::class.java)
                                    myIntent.putExtra("username", user!![0].st_username)
                                    myIntent.putExtra("type", type)
                                    startActivity(myIntent)
                                    finish()
                                Log.d("As6dasd", user!![0].st_username)
                            }
                            "tutor" -> {
                                mLoginPresenter.addDataTutorTioPrefs(applicationContext, user, type)
                                val myIntent = Intent(applicationContext, MainActivity::class.java)
                                myIntent.putExtra("username", user!![0].t_username)
                                myIntent.putExtra("type", type)
                                startActivity(myIntent)
                                finish()
                                Log.d("As6dasd", user[0].t_username)
                            }
                            else -> {
                                mLoginPresenter.addDataAdminToPrefs(applicationContext, user, type)
                                val myIntent = Intent(applicationContext, MainActivity::class.java)
                                myIntent.putExtra("username", user!![0].admin_username)
                                myIntent.putExtra("type", type)
                                startActivity(myIntent)
                                finish()
                                Log.d("As6dasd", user[0].admin_username)
                            }
                        }
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                    }

                    override fun onErrorService(error: String) {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                        val ad: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                        ad.setTitle("พบข้อมผิดพลาด!")
                        ad.setIcon(android.R.drawable.btn_star_big_on)
                        ad.setPositiveButton("ปิด", null)
                        ad.setMessage(error)
                        ad.show()
                        Log.d("As6dasd", error)

                    }
                }
            )
        }
    }

}