package com.example.trippy_android.User

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

fun getRetrofit(): Retrofit {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://trippy-api.store/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()

    return retrofit
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