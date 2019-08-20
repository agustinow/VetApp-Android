package com.odella.vetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.odella.vetapp.service.NetworkService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
