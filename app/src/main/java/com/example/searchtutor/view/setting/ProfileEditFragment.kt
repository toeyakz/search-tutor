package com.example.searchtutor.view.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.searchtutor.R
import com.example.searchtutor.controler.PreferencesData
import com.example.searchtutor.view.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception


@Suppress("DEPRECATION")
class ProfileEditFragment : Fragment() {

    private var supportFragmentManager: FragmentManager? = null
    private lateinit var mSettingPresenter: SettingPresenter
    private var user: PreferencesData.Users? = null

    private lateinit var imageName: File

    private val PICK_IMAGE = 1001

    private var btnRegister: Button? = null
    private var edtName: TextInputEditText? = null
    private var edtLastname: TextInputEditText? = null
    private var edtEmail: TextInputEditText? = null
    private var edtTel: TextInputEditText? = null
    private var edtAddress: TextInputEditText? = null
    private var imgChooseImage: ImageView? = null
    private var imgProfile: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        supportFragmentManager = fragmentManager
        mSettingPresenter = SettingPresenter()
        user = PreferencesData.user(requireActivity())

        btnRegister = root.findViewById(R.id.btnRegister)
        edtName = root.findViewById(R.id.edtName)
        edtLastname = root.findViewById(R.id.edtLastname)
        edtEmail = root.findViewById(R.id.edtEmail)
        edtTel = root.findViewById(R.id.edtTel)
        edtAddress = root.findViewById(R.id.edtAddress)
        imgChooseImage = root.findViewById(R.id.imgChooseImage)
        imgProfile = root.findViewById(R.id.imgProfile)

        setData()
    }

    private fun setData() {
        if(user?.type == "tutor"){
            edtName!!.setText(user?.t_name)
            edtLastname!!.setText(user?.t_lname)
            edtEmail!!.setText(user?.t_email)
            edtTel!!.setText(user?.t_tel)
            edtAddress!!.setText(user?.t_address)
        }else{
            edtName!!.setText(user?.st_name)
            edtLastname!!.setText(user?.st_lname)
            edtEmail!!.setText(user?.st_email)
            edtTel!!.setText(user?.st_phon)
            edtAddress!!.setText(user?.st_address)
        }

        imgChooseImage!!.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }
        btnRegister!!.setOnClickListener {
            if(user?.type == "tutor"){
                mSettingPresenter.upLoadBankDetails(
                    user?.type!!,
                    user?.t_id.toString(),
                    edtName!!.text.toString(),
                    edtLastname!!.text.toString(),
                    edtEmail!!.text.toString(),
                    edtTel!!.text.toString(),
                    edtAddress!!.text.toString(),
                    imageName
                ){
                    if(it){
                        requireFragmentManager().popBackStack()
                        Toast.makeText(activity, "บันทึกข้อมูลบัญชีสำเร็จ!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "พบข้อผิดพลาด!", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                mSettingPresenter.upLoadBankDetails(
                    user?.type!!,
                    user?.st_id.toString(),
                    edtName!!.text.toString(),
                    edtLastname!!.text.toString(),
                    edtEmail!!.text.toString(),
                    edtTel!!.text.toString(),
                    edtAddress!!.text.toString(),
                    imageName
                ){
                    if(it){
                        requireFragmentManager().popBackStack()
                        Toast.makeText(activity, "บันทึกข้อมูลบัญชีสำเร็จ!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "พบข้อผิดพลาด!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("As6dasd", "1")
        if (PICK_IMAGE == requestCode && resultCode == Activity.RESULT_OK) {

            try {
                val pickedImage: Uri = data?.data!!

                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    requireActivity().contentResolver.query(pickedImage, filePath, null, null, null)!!
                cursor.moveToFirst()
                val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(imagePath, options)

                Log.d("As5da1sda", File(imagePath).absolutePath )
                imageName = File(imagePath)

                Picasso.get().load(imageName).into(imgProfile)

            }catch (e: Exception){
                e.printStackTrace()
            }

            // addImageQRCode.setImageBitmap(bitmap)


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

        tvTitle.text = "แก้ไขข้อมูลส่วนตัว"


        back.visibility = View.VISIBLE

        back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }


}