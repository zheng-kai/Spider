package com.example.a27299.myapplication

import android.content.Context
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CourseTable(private val context: Context) {
    fun getCourse() {
        val okHttpClient = OkHttpClientGenerator.generate(context)
        var request = Request.Builder()
                .url("http://classes.tju.edu.cn/eams/courseTableForStd.action\n")
                .get()
                .build()
        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

            }

        })
    }
}