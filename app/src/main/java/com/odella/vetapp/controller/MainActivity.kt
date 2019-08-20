package com.odella.vetapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.odella.vetapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login()
    }

    fun login(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
