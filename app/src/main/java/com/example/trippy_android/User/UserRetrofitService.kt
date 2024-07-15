package com.example.trippy_android.User


import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.trippy_android.User.Login.LoginReq
import com.example.trippy_android.User.Login.LoginView


class UserRetrofitService {
    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginView){
        this.loginView=loginView
    }

    fun login(loginRequest: LoginReq) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        Log.d("memberId: ", loginRequest.memberId)
        Log.d("password: ", loginRequest.password)

        userService.login(loginRequest).enqueue(object : Callback<UserResponse.LoginResponse> {
            override fun onResponse(call: Call<UserResponse.LoginResponse>, response: Response<UserResponse.LoginResponse>) {
                if (response.isSuccessful) {
                    val resp: UserResponse.LoginResponse? = response.body()
                    if (resp != null) {
                        when (val code = resp.code) {
                            "COMMON200" -> loginView.onLoginSuccess(code, resp.result.accessToken)
                            else -> loginView.onLoginFailure(code, resp.message)
                        }
                    } else {
                        Log.e("UserRetrofitService", "Response body is null")
                        loginView.onLoginFailure("null_response", "Response body is null")
                    }
                } else {
                    Log.e("UserRetrofitService", "Unsuccessful response: ${response.errorBody()?.string()}")
                    loginView.onLoginFailure("unsuccessful_response", response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse.LoginResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
                loginView.onLoginFailure("network_failure", t.message.toString())
            }
        })
    }

}