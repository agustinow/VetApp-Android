package com.odella.vetapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog

import com.odella.vetapp.R
import com.odella.vetapp.service.NetworkService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //GlobalScope.launch{
            //delay(1000L)
            checkConnection()
        //}
    }

    fun checkConnection(){
        NetworkService.create().checkConnection().enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
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

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                login()
            }
        })
    }

    fun login(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}