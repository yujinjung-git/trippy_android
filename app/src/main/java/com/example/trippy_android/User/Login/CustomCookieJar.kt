package com.example.trippy_android.User.Login

import android.content.Context
import okhttp3.CookieJar
import okhttp3.Cookie
import okhttp3.HttpUrl

class CustomCookieJar(private val context: Context) : CookieJar {
    private val cookiePrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val JWT_KEY = "jwt"
    private val REFRESH_KEY = "refreshToken"

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val editor = cookiePrefs.edit()
        for (cookie in cookies) {
            if (cookie.name == JWT_KEY || cookie.name == REFRESH_KEY) {
                editor.putString(cookie.name, cookie.value)
            }
        }
        editor.apply()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = mutableListOf<Cookie>()
        val jwt = cookiePrefs.getString(JWT_KEY, null)
        val refreshToken = cookiePrefs.getString(REFRESH_KEY, null)
        if (jwt != null) {
            cookies.add(Cookie.Builder()
                .name(JWT_KEY)
                .value(jwt)
                .domain(url.host)
                .build())
        }
        if (refreshToken != null) {
            cookies.add(Cookie.Builder()
                .name(REFRESH_KEY)
                .value(refreshToken)
                .domain(url.host)
                .build())
        }
        return cookies
    }

    fun addJwtToken(jwt: String) {
        val editor = cookiePrefs.edit()
        editor.putString(JWT_KEY, jwt)
        editor.apply()
    }

    fun getJwtToken(): String? {
        return cookiePrefs.getString(JWT_KEY, null)
    }

    fun clearCookies() {
        val editor = cookiePrefs.edit()
        editor.clear()
        editor.apply()
    }
}