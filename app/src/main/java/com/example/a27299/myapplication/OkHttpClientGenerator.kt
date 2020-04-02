package com.example.a27299.myapplication

import android.content.Context
import com.example.a27299.myapplication.cookie.CookieJarImpl
import com.example.a27299.myapplication.cookie.PersistentCookieStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientGenerator {

    fun generate(context:Context):OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
                .cookieJar(CookieJarImpl(PersistentCookieStore(context)))
                .addNetworkInterceptor(loggingInterceptor).build()
    }
}