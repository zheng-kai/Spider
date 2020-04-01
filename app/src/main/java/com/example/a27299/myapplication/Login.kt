package com.example.a27299.myapplication

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException
import java.util.zip.Deflater

class Login(val ui: UI) {
    private val BASE_URL = "https://sso.tju.edu.cn/cas/login"
    private val url = "$BASE_URL?service=http%3A%2F%2Fclasses.tju.edu.cn%2Feams%2FhomeExt.action%3Bjsessionid%3D94A538044DB0683B3215B606D143D2B5.std6"
    private val logoutUrl = "http://classes.tju.edu.cn/eams/logoutExt.action"
    private var execution = ""
    private val module = Module()
    fun login(userName: String, password: String) =
            GlobalScope.launch {
                val sso = Sso()
                sso.init()
                var doc = Jsoup.connect(url).post()
                var es = doc.select("input")
                for (e in es) {
                    if (e.attr("name") == "execution") {
                        execution = e.`val`()
                    }
                }
                var okHttpClient = OkHttpClient()
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
            Log.d("logout",doc.toString())
        }
    }
}