package com.example.searchtutor.view.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.searchtutor.R
import com.example.searchtutor.controler.CustomProgressDialog
import com.example.searchtutor.data.response.qaList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File

class RegisterActivity : AppCompatActivity() {

    private lateinit var mLoginPresenter: LoginPresenter
    private var radioValue: String = ""
    private var dialog: CustomProgressDialog? = null

    private val PICK_IMAGE = 1003
    private var imageName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mLoginPresenter = LoginPresenter()
        actionOnClick()
    }

    private fun actionOnClick() {

        btnAddImageCard.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioTutor -> {
                    constraintLayout5.visibility = View.VISIBLE
                    textInputLayout86.visibility = View.VISIBLE
                }
                R.id.radioUser -> {
                    constraintLayout5.visibility = View.GONE
                    textInputLayout86.visibility = View.GONE
                }
            }
        }

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
                val idCard = edtIdCard.text.toString()

                mLoginPresenter.sendRegister(
                    this,
                    username,
                    password,
                    name,
                    lastName,
                    email,
                    tel,
                    address,
                    idCard,
                    radioValue,
                    imageName
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
        } else {
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

                if (imageName == "") {
                    ad.setMessage("กรุณาแนปรูปบัตรประชาชนก่อน")
                    ad.show()
                    radioGroup.requestFocus()
                    return false
                } else {

                }

            }
            if (radioUser.isChecked) {
                radioValue = "user"
            }
        }
        return true
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (PICK_IMAGE == requestCode && resultCode == Activity.RESULT_OK) {
            val pickedImage: Uri = data?.data!!

            val filePath = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor = contentResolver.query(pickedImage, filePath, null, null, null)!!
            cursor.moveToFirst()
            val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
            imageName = imagePath

            val file = File(imageName)

            tvFileName.text = file.name
            Picasso.get().load(File(imagePath)).into(btnAddImageCard)

        }


    }
}