package com.example.a27299.myapplication

import android.util.Log

import okhttp3.*
import java.io.IOException
import java.net.CookieHandler

class Sso {
    private val BASE_URL = "http://classes.tju.edu.cn/eams/homeExt.action"
    private var ssoUrl :String?= ""
    fun init() {
        var okHttpClient = OkHttpClient()
        var request = Request.Builder().url(BASE_URL).get().build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val headers = response.headers()
                ssoUrl = headers.get("Location")
                Log.d("ssoHeaders",headers.toString())
                Log.d("ssoUrl",ssoUrl)
                Log.d("cookies:", headers.toMultimap()["set-cookie"]?.get(0) ?:"")
            }
        })
    }
}