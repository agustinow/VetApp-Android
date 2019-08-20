package com.odella.vetapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog

import com.odella.vetapp.R
import com.odella.vetapp.service.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var networkService: NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkService = NetworkService.create()
        checkConnection()
    }

    fun checkConnection(){
        networkService.checkConnection().enqueue(object: Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                MaterialDialog(this@MainActivity).show{
                    title(R.string.network_error)
                    positiveButton(R.string.try_again) {
                        it.dismiss()
                        checkConnection()
                    }
                    negativeButton(R.string.close) {
                        this@MainActivity.finish()
                    }
                }
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                login()
            }

        })
    }

    fun login(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
