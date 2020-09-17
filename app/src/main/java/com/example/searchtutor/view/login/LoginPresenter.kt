package com.example.searchtutor.view.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.searchtutor.controler.Constants
import com.example.searchtutor.controler.Utils
import com.example.searchtutor.data.api.DataModule
import com.example.searchtutor.data.body.UploadProfile
import com.example.searchtutor.data.model.User
import com.example.searchtutor.data.response.ImageReturn
import com.example.searchtutor.data.response.LoginResponse
import com.example.searchtutor.data.response.RegisterResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit

class LoginPresenter {

    interface View {
        fun onSuccessService(user: List<User>?, type: String)
        fun onErrorService(error: String)
    }


    @SuppressLint("CheckResult")
    fun callLogin(context: Context, user: String, pass: String, view: View) {

        DataModule.instance()!!.login(user, pass)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<LoginResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: LoginResponse) {
                    if (t.isSuccessful) {

                        view.onSuccessService(t.data, t.type)
                        /* userToSheared(context, t.data!!)
                         view?.onSuccessService(t.data)*/

                    } else {
                        when (t.message) {
                            "รหัสผ่านไม่ถูกต้อง" -> {
                                view.onErrorService("รหัสผ่านไม่ถูกต้อง")
                            }
                            "ไม่พบชื่อผู้ใช้" -> {
                                view.onErrorService("ไม่พบชื่อผู้ใช้")
                            }

                        }

                    }


                }

                override fun onError(e: Throwable) {
                    view.onErrorService(e.message.toString())
                    Log.d("s8ssa9c2d5asd", e.message)
                }
            })
    }

    fun addDataTutorTioPrefs(context: Context, user: List<User>?, type: String) {
        val pf: SharedPreferences =
            context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putBoolean("session", true)
        editor.putString("type", type)
        editor.putInt("t_id", user!![0].t_id!!)
        editor.putString("t_username", user[0].t_username.toString())
        editor.putString("t_password", user[0].t_password.toString())
        editor.putString("t_name", user[0].t_name.toString())
        editor.putString("t_lname", user[0].t_lname.toString())
        editor.putString("t_email", user[0].t_email.toString())
        editor.putString("t_tel", user[0].t_tel.toString())
        editor.putString("t_address", user[0].t_address.toString())
        editor.apply()
    }

    fun addDataAdminToPrefs(context: Context, user: List<User>?, type: String) {
        val pf: SharedPreferences =
            context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putBoolean("session", true)
        editor.putString("type", type)
        editor.putInt("admin_id", user!![0].admin_id!!)
        editor.putString("admin_username", user[0].admin_username.toString())
        editor.putString("admin_password", user[0].admin_password.toString())
        editor.apply()
    }

    fun addDataUserToPrefs(context: Context, user: List<User>?, type: String) {
        val pf: SharedPreferences =
            context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putBoolean("session", true)
        editor.putString("type", type)
        editor.putInt("st_id", user!![0].st_id!!)
        editor.putString("st_username", user[0].st_username.toString())
        editor.putString("st_password", user[0].st_password.toString())
        editor.putString("st_name", user[0].st_name.toString())
        editor.putString("st_lname", user[0].st_lname.toString())
        editor.putString("st_email", user[0].st_email.toString())
        editor.putString("st_phon", user[0].st_phon.toString())
        editor.putString("st_address", user[0].st_address.toString())
        editor.apply()
    }

    @SuppressLint("CheckResult")
    fun sendRegister(
        context: Context,
        username: String,
        password: String,
        name: String,
        lastName: String,
        email: String,
        tel: String,
        address: String,
        idCard: String,
        radioValue: String,
        imageName: String,
        res: (Boolean, String) -> Unit
    ) {
        val encodedImagePic1: String
        val uploadImage = ArrayList<UploadProfile.Data>()

        val conTactArray = JSONArray()
        val root = JSONObject()
        val contact = JSONObject()

        val file = File(imageName)

        if (file.absolutePath != "") {
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)

            if (myBitmap != null) {
                Log.d("ASd6asd", myBitmap.toString())
                val byteArrayOutputStream =
                    ByteArrayOutputStream()
                myBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    70,
                    byteArrayOutputStream
                )
                val byteArrayImage =
                    byteArrayOutputStream.toByteArray()
                encodedImagePic1 = Base64.encodeToString(
                    byteArrayImage,
                    Base64.DEFAULT
                )

                contact.put("username", username)
                contact.put("password", password)
                contact.put("name", name)
                contact.put("last_name", lastName)
                contact.put("email", email)
                contact.put("address", address)
                contact.put("tel", tel)
                contact.put("type", radioValue)
                contact.put("idCard", idCard)

                contact.put("name_image", file.name)
                contact.put("img_base64", "data:image/jpeg;base64,$encodedImagePic1")

                conTactArray.put(0, contact)
                root.put("data", conTactArray)

            } else {

                contact.put("username", username)
                contact.put("password", password)
                contact.put("name", name)
                contact.put("last_name", lastName)
                contact.put("email", email)
                contact.put("address", address)
                contact.put("tel", tel)
                contact.put("type", radioValue)
                contact.put("idCard", idCard)

                contact.put("name_image", "")
                contact.put("img_base64", "")

                conTactArray.put(0, contact)
                root.put("data", conTactArray)

            }


        }

        val rootToString: String = root.toString()
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            rootToString
        )

        val json: String = Utils().getGson()!!.toJson(rootToString)
        Log.d("a9a20as8da", json)


        DataModule.instance()!!
            .upLoadRegister(body)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageReturn>() {
                override fun onComplete() {

                }

                override fun onNext(t: ImageReturn) {
                    if (t.isSuccessful) {
                        res.invoke(true, t.message.toString())
                        Log.d("As85das1d", t.message)
                    } else {
                        res.invoke(false, t.message.toString())
                    }
                }

                @SuppressLint("DefaultLocale")
                override fun onError(e: Throwable) {
                    res.invoke(false, e.message.toString())
                    Log.d("As85das1d", e.message)
                }
            })


    }

    @SuppressLint("CheckResult")
    fun sendDataToServer(
        context: Context,
        username: String,
        password: String,
        name: String,
        lastName: String,
        email: String,
        tel: String,
        address: String,
        radioValue: String,
        res: (Boolean, String) -> Unit
    ) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("username", username)
            contact.put("password", password)
            contact.put("name", name)
            contact.put("last_name", lastName)
            contact.put("email", email)
            contact.put("address", address)
            contact.put("tel", tel)
            contact.put("type", radioValue)

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("RegisterPresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.register(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<RegisterResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: RegisterResponse) {
                        Log.d("d7s2dfg9sf", t.isSuccessful.toString())
                        if (t.isSuccessful) {
                            res.invoke(true, t.message.toString())
                        } else {
                            res.invoke(false, t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("as98a6sasc", e.message.toString() + "2")

                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}