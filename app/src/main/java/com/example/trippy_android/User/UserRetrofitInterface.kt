package com.example.trippy_android.User

import com.example.trippy_android.User.Login.LoginReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserRetrofitInterface {
    @Headers("Content-Type: application/json")
    @POST("/api/member/login")
    fun login(@Body loginReq: LoginReq): Call<UserResponse.LoginResponse>

}