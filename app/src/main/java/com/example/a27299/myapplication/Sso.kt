package com.example.a27299.myapplication

import android.content.Context
import android.util.Log
import com.example.a27299.myapplication.cookie.CookieJarImpl
import com.example.a27299.myapplication.cookie.PersistentCookieStore

import okhttp3.*
import java.io.IOException
import java.net.CookieHandler

class Sso(val context: Context) {
    private val BASE_URL = "http://classes.tju.edu.cn/eams/homeExt.action"
    private var ssoUrl: String? = ""
    fun init() {
        var okHttpClient = OkHttpClient.Builder()
                .cookieJar(CookieJarImpl(PersistentCookieStore(context))).build()
        var request = Request.Builder().url(BASE_URL).get().build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

            }
        })
    }
}