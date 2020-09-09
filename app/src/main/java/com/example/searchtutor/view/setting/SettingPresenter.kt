package com.example.searchtutor.view.setting

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
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
import com.example.searchtutor.data.response.*
import com.example.searchtutor.view.home.HomePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit

class SettingPresenter {

    interface Response {
        interface Tutor {
            fun value(c: TutorResponse)
            fun error(c: String?)
        }

        interface Student {
            fun value(c: StudentResponse)
            fun error(c: String?)
        }

    }

    @SuppressLint("CheckResult")
    fun updateTutoring(
        i_id: String?,
        h: HashMap<String, String>,
        function: (Boolean, String) -> Unit
    ) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("t_id", i_id)
            contact.put("t_tutoring_center", h["tutoring"])

            conTactArray.put(0, contact)
            root.put("data", conTactArray)



            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            val json: String = Utils().getGson()!!.toJson(root)
            Log.d("a9a20as8da", rootToString)

            DataModule.instance()!!.updateTutoring(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<UpdateCourseResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: UpdateCourseResponse) {
                        Log.d("d7s2dfg9sf", t.isSuccessful.toString())
                        if (t.isSuccessful) {
                            function.invoke(true, t.message.toString())
                        } else {
                            function.invoke(false, t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        function.invoke(false, e.message.toString())
                        Log.d("as98a6sasc", e.message.toString() + "2")

                    }
                })


        } catch (e: Exception) {
            function.invoke(false, e.message.toString())
            // function.invoke(false, e.message!!)
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getAllStudent(response: Response.Student) {
        DataModule.instance()!!.getAllStudent()
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<StudentResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: StudentResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun getAllTutor(response: Response.Tutor) {
        DataModule.instance()!!.getAllTutor()
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TutorResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: TutorResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }


    @SuppressLint("CheckResult")
    fun upLoadBankDetails(
        type: String,
        id_: String,
        name: String,
        last_name: String,
        email: String,
        tel: String,
        address: String,
        img: String,
        file: File?,
        res: (Boolean) -> Unit
    ) {
        val encodedImagePic1: String
        val uploadImage = ArrayList<UploadProfile.Data>()

        val conTactArray = JSONArray()
        val root = JSONObject()
        val contact = JSONObject()

        if (file?.absolutePath != "") {
            val myBitmap = BitmapFactory.decodeFile(file?.absolutePath)

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

                contact.put("type", type)
                contact.put("id_", id_)
                contact.put("name", name)
                contact.put("last_name", last_name)
                contact.put("email", email)
                contact.put("tel", tel)
                contact.put("address", address)
                contact.put("name_image", file!!.name)
                contact.put("img_base64", "data:image/jpeg;base64,$encodedImagePic1")

                conTactArray.put(0, contact)
                root.put("data", conTactArray)

            } else {

                contact.put("type", type)
                contact.put("id_", id_)
                contact.put("name", name)
                contact.put("last_name", last_name)
                contact.put("email", email)
                contact.put("tel", tel)
                contact.put("address", address)
                contact.put("name_image", img)
                contact.put("img_base64", "")

                conTactArray.put(0, contact)
                root.put("data", conTactArray)

                /*          val uploadData = UploadProfile.Data(
                              type,
                              id_,
                              name,
                              last_name,
                              email,
                              tel,
                              address,
                              img,
                              "data:image/jpeg;base64,"
                          )
                          uploadImage.add(uploadData)*/
            }


        }

        val rootToString: String = root.toString()
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            rootToString
        )

        val json: String = Utils().getGson()!!.toJson(body)
        Log.d("a9a20as8da", json)


        DataModule.instance()!!
            //.upLoadProfile(UploadProfile(uploadImage))
            .upLoadProfile(body)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageReturn>() {
                override fun onComplete() {

                }

                override fun onNext(t: ImageReturn) {
                    if (t.isSuccessful) {
                        res.invoke(true)
                        Log.d("As85das1d", t.message)
                    } else {
                        res.invoke(false)
                    }
                }

                @SuppressLint("DefaultLocale")
                override fun onError(e: Throwable) {
                    res.invoke(false)
                    Log.d("As85das1d", e.message)
                }
            })


    }


    @SuppressLint("CheckResult")
    fun getStudent(st_id: String, response: Response.Student) {
        DataModule.instance()!!.getStudent(st_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<StudentResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: StudentResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }


    @SuppressLint("CheckResult")
    fun getTutor(t_id: String, response: Response.Tutor) {
        DataModule.instance()!!.getTutor(t_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TutorResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: TutorResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun addComment(
        t_id_key: String,
        t_id: String,
        st_id: String,
        cm_detail: String,
        response: HomePresenter.Response.AddComment
    ) {
        DataModule.instance()!!.addComment(t_id_key, t_id, st_id, cm_detail)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<AddCommentResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: AddCommentResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun deleteComment(cm_id: String?, function: (Boolean, String) -> Unit) {

        try {
            DataModule.instance()!!.deleteComment(cm_id.toString())
                .subscribeOn(Schedulers.io())
                .timeout(20, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DeleteCourseResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: DeleteCourseResponse) {
                        function.invoke(true, t.message!!)
                    }

                    override fun onError(e: Throwable) {
                        function.invoke(false, e.message!!)
                    }
                })
        } catch (e: Exception) {
            function.invoke(false, e.message!!)
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getComment(t_id: String, response: HomePresenter.Response.Comment) {
        DataModule.instance()!!.getComment(t_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CommentResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: CommentResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    fun logout(activity: Context, res: (Boolean) -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("ออกจากระบบ")
            .setMessage("คุณณต้องการออกจากระบบหรือไม่?")
            .setPositiveButton(
                "ออกจากระบบ"
            ) { _, _ ->
                val preferences: SharedPreferences = activity.getSharedPreferences(
                    Constants.PREFERENCES_USER,
                    Context.MODE_PRIVATE
                )
                preferences.edit().putBoolean(Constants.SESSION, false).apply()
                preferences.edit().clear().apply()

                res.invoke(true)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                res.invoke(false)
            }
            .show()
    }

}