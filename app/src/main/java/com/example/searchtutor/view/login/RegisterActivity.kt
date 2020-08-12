package com.example.searchtutor.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mLoginPresenter: LoginPresenter
    private var radioValue: String = ""
    private var dialog: CustomProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mLoginPresenter = LoginPresenter()
        actionOnClick()
    }

    private fun actionOnClick() {


        btnRegister.setOnClickListener {
            dialog = CustomProgressDialog(this, "กำลังโหลด..")
            dialog?.show()

            if (!checkIsEmpty()) {
                if (dialog?.isShowing!!) {
                    dialog?.dismiss()
                }
            } else {

                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                val name = edtName.text.toString()
                val lastName = edtLastname.text.toString()
                val email = edtEmail.text.toString()
                val tel = edtTel.text.toString()
                val address = edtAddress.text.toString()

                mLoginPresenter.sendDataToServer(
                    this,
                    username,
                    password,
                    name,
                    lastName,
                    email,
                    tel,
                    address,
                    radioValue
                ) { b, t ->
                    val ad: AlertDialog.Builder = AlertDialog.Builder(this)
                    ad.setTitle("พบข้อมผิดพลาด! ")
                    ad.setIcon(android.R.drawable.btn_star_big_on)
                    ad.setPositiveButton("ปิด", null)
                    if (b) {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                        Toast.makeText(this, t, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        if (dialog?.isShowing!!) {
                            dialog?.dismiss()
                        }
                        ad.setMessage(t)
                        ad.show()
                    }
                }

            }

        }
    }


    private fun checkIsEmpty(): Boolean {
        // Dialog
        val ad: AlertDialog.Builder = AlertDialog.Builder(this)
        ad.setTitle("พบข้อมผิดพลาด!")
        ad.setPositiveButton("ปิด", null)

        // Check Username
        if (edtUsername.text?.isEmpty()!!) {
            ad.setMessage("กรุณากรอกชื่อผู้ใช้")
            ad.show()
            edtUsername.requestFocus()
            return false
        }else{
            textInputLayout.error = null
        }

        // Check Password
        if (edtPassword.text!!.isEmpty()) {
            ad.setMessage("กรุณากรอกรหัสผ่าน")
            ad.show()
            edtPassword.requestFocus()
            return false
        }
        // Check Name
        if (edtName.text?.isEmpty()!!) {
            ad.setMessage("กรุณากรอกชื่อ")
            ad.show()
            edtName.requestFocus()
            return false
        }

        // Check Email
        if (edtLastname.text?.isEmpty()!!) {
            ad.setMessage("กรุณากรอกนามสกุล")
            ad.show()
            edtLastname.requestFocus()
            return false
        }

        // Check Email
        if (edtEmail.text?.isEmpty()!!) {
            ad.setMessage("กรุณากรอกอีเมล์")
            ad.show()
            edtEmail.requestFocus()
            return false
        }

        // Check Tel
        if (edtTel.text?.isEmpty()!!) {
            ad.setMessage("กรุณากรอกเบอร์โทร")
            ad.show()
            edtTel.requestFocus()
            return false
        }

        if (radioGroup.checkedRadioButtonId == -1) {
            ad.setMessage("กรุณาเลือกประเภทก่อน")
            ad.show()
            radioGroup.requestFocus()
            return false
        } else {
            if (radioTutor.isChecked) {
                radioValue = "tutor"
            }
            if (radioUser.isChecked) {
                radioValue = "user"
            }
        }
        return true
    }
}