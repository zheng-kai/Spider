package com.example.a27299.myapplication

import android.util.Log
import okhttp3.Response

class Module {
    fun storeResponse(response:Response){
        Log.d("responseHeaders",response.headers().toString())
        Log.d("responseBody",response.body().toString())
    }
}