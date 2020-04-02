package com.example.a27299.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.jsoup.Jsoup
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class MainActivity : AppCompatActivity(), UI {

    private lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = "3018216157"
        var password = "19991002Zk"
        tv = findViewById(R.id.tv)
        val sso = Sso(this,this)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnMain = findViewById<Button>(R.id.btn_main)
        val btnCt = findViewById<Button>(R.id.btn_coursetable)

        val ct = CourseTable(this)
        btnMain.setOnClickListener {
            sso.init()
        }
        btnLogout.setOnClickListener {
            sso.logout()

        }
        btnLogin.setOnClickListener {
            sso.login(username, password)
        }
        btnCt.setOnClickListener {
            ct.getCourse()

        }
    }

    override fun fail() {
        tv.text = "失败"
    }

    override fun success() {
        tv.text = "成功"
    }
}