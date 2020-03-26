package com.example.a27299.myapplication

import android.util.Log
import okhttp3.Response

class Module {
    fun storeResponse(response:Response){
        Log.d("response",response.headers().toString())
    }
}