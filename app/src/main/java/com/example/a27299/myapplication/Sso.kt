package com.example.a27299.myapplication

import android.content.Context
import android.util.Log
import com.example.a27299.myapplication.cookie.CookieJarImpl
import com.example.a27299.myapplication.cookie.PersistentCookieStore

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Jsoup
import java.io.IOException

public class Sso(ui: UI, private val context: Context) {
    private val BASE_URL = "http://classes.tju.edu.cn/eams/homeExt.action"
    private var ssoUrl: String? = ""
    private val loggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    private var execution = ""
    private var user: User = User(ui, context)


    fun init() {
        var okHttpClient = OkHttpClient.Builder()
                .cookieJar(CookieJarImpl(PersistentCookieStore(context)))
                .addNetworkInterceptor(loggingInterceptor).build()
        var request = Request.Builder()
                .url(BASE_URL)
                .get()
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()
                val headers = response.headers()

                var doc = Jsoup.parse(body?.string())
                var es = doc.select("input")
                for (e in es) {
                    if (e.attr("name") == "execution") {
                        execution = e.`val`()
                    }
                }
                ssoUrl = headers.get("Location")
                Log.d("execution", execution)
                Log.d("ssoUrl", ssoUrl ?: "")
            }
        })
    }
    fun login(userName:String,password:String){
        user.login(userName,password,execution)
    }
    fun logout(){
        user.logout()
    }
}