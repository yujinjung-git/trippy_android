package com.example.trippy_android.User


import android.content.Context
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.trippy_android.User.Login.CustomCookieJar
import com.example.trippy_android.User.Login.LoginReq
import com.example.trippy_android.User.Login.LoginView
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl


class UserRetrofitService(private val context: Context) {
    private lateinit var loginView: LoginView
    private val cookieJar = CustomCookieJar(context)
    private val JWT_KEY = "jwt"

    fun setLoginView(loginView: LoginView){
        this.loginView=loginView
    }

    fun login(loginRequest: LoginReq) {
        val userService = getRetrofit(context).create(UserRetrofitInterface::class.java)
        Log.d("memberId: ", loginRequest.memberId)
        Log.d("password: ", loginRequest.password)

        userService.login(loginRequest).enqueue(object : Callback<UserResponse.LoginResponse> {
            override fun onResponse(call: Call<UserResponse.LoginResponse>, response: Response<UserResponse.LoginResponse>) {
                if (response.isSuccessful) {

                    val url = "https://trippy-api.store/".toHttpUrl()
                    val cookies = response.headers().values("Set-Cookie").mapNotNull { Cookie.parse(url, it) }
                    cookieJar.saveFromResponse(url, cookies)
                    val resp: UserResponse.LoginResponse? = response.body()
                    if (resp != null) {
                        when (val code = resp.code) {
                            "COMMON200" -> {
                                loginView.onLoginSuccess(code, resp.result.accessToken)
                                cookieJar.addJwtToken(resp.result.accessToken)

                            }
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
    fun loginExtension() {
        val userService = getRetrofit(context).create(UserRetrofitInterface::class.java)
        userService.loginExtension().enqueue(object : Callback<UserResponse.LoginResponse> {

            override fun onResponse(
                call: Call<UserResponse.LoginResponse>,
                response: Response<UserResponse.LoginResponse>
            ) {
                Log.d("jwtjwtjwtjwt", cookieJar.getJwtToken().toString())
                if (response.isSuccessful) {
                    val resp = response.body()!!
                    Log.d("UserRetrofitService", "Response body: $resp")
                    when (val code = resp.code) {
                        "COMMON200" -> {
                            Log.d("Token refreshed", "New access token: ${resp.result.accessToken}")

                            // CustomCookieJar에 저장된 JWT를 가져와서 "Bearer " 형식으로 저장
                            cookieJar.addJwtToken(resp.result.accessToken)

                        }

                        else -> Log.e("Token refresh error", resp.message)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Token refresh failed", "Unsuccessful response: $errorBody")
                }
            }

            override fun onFailure(call: Call<UserResponse.LoginResponse>, t: Throwable) {
                Log.e("Token refresh error", t.message.toString())
            }
        })
    }
}