package com.example.searchtutor.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.searchtutor.data.api.DataModule
import com.example.searchtutor.data.response.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.math.log

class HomePresenter {

    interface Response {
        interface CategoryName {
            fun value(c: CategoryNameResponse)
            fun error(c: String?)
        }

        interface Category {
            fun value(c: CategoryResponse)
            fun error(c: String?)
        }

        interface AddGroupCategory {
            fun value(c: AddGroupCategoryResponse)
            fun error(c: String?)
        }

        interface AddCourse {
            fun value(c: AddCourseResponse)
            fun error(c: String?)
        }

        interface UpdateGroupCategory {
            fun value(c: UpdateGroupCategoryResponse)
            fun error(c: String?)
        }

        interface GroupCourse {
            fun value(c: GroupCourseResponse)
            fun error(c: String?)
        }
    }

    @SuppressLint("CheckResult")
    fun updateCourse(
        gc_id: String?,
        h: HashMap<String, String>,
        function: (Boolean, String) -> Unit
    ) {
        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("gc_id", h["gc_id"])
            contact.put("gc_name", h["gc_name"])
            contact.put("gc_detail", h["gc_detail"])
            contact.put("gc_price", h["gc_price"])

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            Log.d("HomePresenter", root.toString())

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.updateCourse(body)
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
    fun deleteCourse(gc_id: String?, function: (Boolean, String) -> Unit) {

        try {
            DataModule.instance()!!.deleteCourse(gc_id.toString())
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
    fun addCourse(
        tutor_id: String,
        g_id: String,
        c_name: String,
        c_detail: String,
        c_price: String,
        response: Response.AddCourse
    ) {

        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("g_id", g_id)
            contact.put("t_id", tutor_id)
            contact.put("gc_name", c_name)
            contact.put("gc_detail", c_detail)
            contact.put("gc_price", c_price)

            conTactArray.put(0, contact)
            root.put("data", conTactArray)


            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.addCourse(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<AddCourseResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: AddCourseResponse) {
                        if (t.isSuccessful) {
                            response.value(t)
                        } else {
                            response.error(t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("d7s2dfg9sf", e.message.toString() + "2")

                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("d7s2dfg9sf", e.message.toString() + "2")
        }
    }

    @SuppressLint("CheckResult")
    fun getGroupCourse(g_id: String, response: Response.GroupCourse) {
        DataModule.instance()!!.getGroupCourse(g_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<GroupCourseResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: GroupCourseResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }


    @SuppressLint("CheckResult")
    fun getGroupCategory(tutor_id: String, response: Response.CategoryName) {
        DataModule.instance()!!.getGroupCategory(tutor_id)
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CategoryNameResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: CategoryNameResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun getCategory(response: Response.Category) {
        DataModule.instance()!!.getCategory()
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CategoryResponse>() {
                override fun onComplete() {
                }

                override fun onNext(t: CategoryResponse) {
                    response.value(t)
                }

                override fun onError(e: Throwable) {
                    response.error(e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun addGroupCategory(
        tutor_id: String,
        ca_id: String,
        response: Response.AddGroupCategory
    ) {

        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("tutor_id", tutor_id)
            contact.put("ca_id", ca_id)

            conTactArray.put(0, contact)
            root.put("data", conTactArray)


            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.addGroupCategory(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<AddGroupCategoryResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: AddGroupCategoryResponse) {
                        if (t.isSuccessful) {
                            response.value(t)
                        } else {
                            response.error(t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("d7s2dfg9sf", e.message.toString() + "2")

                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("d7s2dfg9sf", e.message.toString() + "2")
        }
    }

    @SuppressLint("CheckResult")
    fun updateGroupCategory(
        g_id: String,
        tutor_id: String,
        ca_id: String,
        response: Response.UpdateGroupCategory
    ) {

        try {

            val conTactArray = JSONArray()
            val root = JSONObject()
            val contact = JSONObject()

            contact.put("g_id", g_id)
            contact.put("tutor_id", tutor_id)
            contact.put("ca_id", ca_id)

            conTactArray.put(0, contact)
            root.put("data", conTactArray)

            val rootToString: String = root.toString()
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                rootToString
            )

            DataModule.instance()!!.updateGroupCategory(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<UpdateGroupCategoryResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: UpdateGroupCategoryResponse) {
                        if (t.isSuccessful) {
                            response.value(t)
                        } else {
                            response.error(t.message.toString())
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    override fun onError(e: Throwable) {
                        Log.d("d7s2dfg9sf", e.message.toString() + "2")

                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("d7s2dfg9sf", e.message.toString() + "2")
        }
    }


}