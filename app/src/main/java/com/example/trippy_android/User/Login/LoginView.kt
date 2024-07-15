package com.example.trippy_android.User.Login

interface LoginView {
    fun onLoginSuccess(code:String, jwt:String)
    fun onLoginFailure(code:String, msg:String)
}