package com.example.a27299.myapplication

import android.content.Context
import android.util.Log
import com.example.a27299.myapplication.cookie.CookieJarImpl
import com.example.a27299.myapplication.cookie.PersistentCookieStore
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Jsoup
import java.io.IOException
import java.util.zip.Deflater

class User(val ui: UI, private val context: Context) {
    private val BASE_URL = "https://sso.tju.edu.cn/cas/login"
    private val url = "$BASE_URL?service=http://classes.tju.edu.cn/eams/homeExt.action"
    private val logoutUrl = "http://classes.tju.edu.cn/eams/logoutExt.action"
    private val module = Module()
    fun login(userName: String, password: String, execution: String) =
            GlobalScope.launch {
                var okHttpClient = OkHttpClientGenerator.generate(context)
                var requestBody = FormBody.Builder()
                        .add("username", userName)
                        .add("password", password)
                        .add("_eventId", "submit")
                        .add("execution", execution)
                        .build()
                var request = Request.Builder().url(url).post(requestBody).build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        GlobalScope.launch(Dispatchers.Main) {
                            ui.fail()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d("cookie", response.header("Set-Cookie") ?: "null")
                        module.storeResponse(response)
                        GlobalScope.launch(Dispatchers.Main) {
                            ui.success()
                        }
                    }

                })
            }


    fun logout() {
        GlobalScope.launch {
            var doc = Jsoup.connect(logoutUrl).get()
            Log.d("logout", doc.toString())
        }
    }
}