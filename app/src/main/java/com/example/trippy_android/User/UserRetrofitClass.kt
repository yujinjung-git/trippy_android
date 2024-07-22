package com.example.trippy_android.User

import android.content.Context
import com.example.trippy_android.User.Login.CustomCookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

fun getRetrofit(context: Context): Retrofit {
    val client=OkHttpClient.Builder()
        .cookieJar(CustomCookieJar(context))
        .build()

    return Retrofit.Builder()
        .baseUrl("https://trippy-api.store/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


}


class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest=request().newBuilder()
            .addHeader("(header key)", "(header value)")
            .build()
        proceed(newRequest)
    }
}