package com.example.searchtutor.data.response

import com.example.searchtutor.data.model.User


data class LoginResponse(
    val isSuccessful: Boolean,
    val message: String,
    val type: String,
    val data: List<User>?
)