package com.example.searchtutor.controler

import android.content.Context

class PreferencesData {
    companion object {
        fun user(context: Context): Users {
            val prefs =
                context.getSharedPreferences(Constants.PREFERENCES_USER, Context.MODE_PRIVATE)
            val session = prefs.getBoolean("session", false)
            val type = prefs.getString("type", "")
            val st_id = prefs.getInt("st_id", 0)
            val st_username = prefs.getString("st_username", "")
            val st_password = prefs.getString("st_password", "")
            val st_name = prefs.getString("st_name", "")
            val st_lname = prefs.getString("st_lname", "")
            val st_email = prefs.getString("st_email", "")
            val st_phon = prefs.getString("st_phon", "")
            val st_address = prefs.getString("st_address", "")

            //admin
            val admin_id = prefs.getInt("admin_id", 0)
            val admin_username = prefs.getString("admin_username", "")
            val admin_password = prefs.getString("admin_password", "")
            //tutor
            val t_id = prefs.getInt("t_id", 0)
            val t_username = prefs.getString("t_username", "")
            val t_password = prefs.getString("t_password", "")
            val t_name = prefs.getString("t_name", "")
            val t_lname = prefs.getString("t_lname", "")
            val t_email = prefs.getString("t_email", "")
            val t_tel = prefs.getString("t_tel", "")
            val t_address = prefs.getString("t_address", "")


            return Users(
                session,
                type,
                st_id,
                st_username,
                st_password,
                st_name,
                st_lname
                ,
                st_email,
                st_phon,
                st_address,

                admin_id,
                admin_username,
                admin_password
                ,
                t_id,
                t_username,
                t_password,
                t_name,
                t_lname,
                t_email,
                t_tel,
                t_address
            )
        }


    }


    data class Users(
        val session: Boolean? = false,
        val type: String? = null,
        //user
        var st_id: Int? = null,
        var st_username: String? = null,
        var st_password: String? = null,
        var st_name: String? = null,
        var st_lname: String? = null,
        var st_email: String? = null,
        var st_phon: String? = null,
        var st_address: String? = null,

        //admin
        var admin_id: Int? = null,
        var admin_username: String? = null,
        var admin_password: String? = null,
        //tutor
        var t_id: Int? = null,
        var t_username: String? = null,
        var t_password: String? = null,
        var t_name: String? = null,
        var t_lname: String? = null,
        var t_email: String? = null,
        var t_tel: String? = null,
        var t_address: String? = null
    )


}