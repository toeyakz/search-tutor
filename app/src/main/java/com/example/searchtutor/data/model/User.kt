package com.example.searchtutor.data.model

data class User (
    val session: Boolean? = false,
    //user
    var st_id : Int? = null,
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
    var t_id : Int? = null,
    var t_username: String? = null,
    var t_password: String? = null,
    var t_name: String? = null,
    var t_lname: String? = null,
    var t_email: String? = null,
    var t_tel: String? = null,
    var t_address: String? = null
)