package com.odella.vetapp.controller

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProviders
import com.odella.vetapp.R
import com.odella.vetapp.constants.PREFS_NAME
import com.odella.vetapp.constants.PREFS_USERNAME
import com.odella.vetapp.constants.TokenSingleton
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var model: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        model = ViewModelProviders.of(this@LoginActivity)[LoginViewModel::class.java]

        val pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(PREFS_USERNAME, "")
        if(pref.isNullOrEmpty()){
            model.rememberMe = false
            chkRememberMe.isChecked = false
        } else {
            model.rememberMe = true
            chkRememberMe.isChecked = true
            txtUsername.setText(pref)
            txtPassword.requestFocus()
        }

        btnLogIn.setOnClickListener {
            model.username = txtUsername.text.toString()
            model.password = txtPassword.text.toString()
            tryToConnect(model.username, model.password)
        }

        switchPassword.isChecked = model.passwordVisible

        switchPassword.setOnClickListener {
            if(model.passwordVisible){
                model.passwordVisible = false
                val start = txtPassword.selectionStart
                val end = txtPassword.selectionEnd
                txtPassword.transformationMethod = PasswordTransformationMethod()
                txtPassword.setSelection(start, end)
            } else {
                model.passwordVisible = true
                val start = txtPassword.selectionStart
                val end = txtPassword.selectionEnd
                txtPassword.transformationMethod = null
                txtPassword.setSelection(start, end)

            }
        }
    }

    fun tryToConnect(username:String,password:String){
        val request = JSONObject()
        request.put("Username", username)
        request.put("Password", password)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), request.toString())
        val call = NetworkService.create().login(requestBody)
        call.enqueue(object: Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                makeText(this@LoginActivity, "Impossible to connect", LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.code() == 200){
                    TokenSingleton.actualToken = "Bearer "  + response.body().toString()
                    login()
                } else {
                    makeText(this@LoginActivity, "Invalid user", LENGTH_SHORT).show()
                    txtUsername.setText("")
                    txtPassword.setText("")
                }
            }

        })

    }

    fun login(){
        makeText(this@LoginActivity, "Good", LENGTH_SHORT).show()
        if(chkRememberMe.isChecked) {
            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_USERNAME, model.username)
                .apply()
        } else {
            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_USERNAME, "")
                .apply()
        }
        //val intent = Intent(this@LoginActivity, ClientMainActivity::class.java)
        //startActivity(intent)
    }


}



