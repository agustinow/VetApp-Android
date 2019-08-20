package com.odella.vetapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.lifecycle.ViewModelProviders
import com.odella.vetapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var model: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        model = ViewModelProviders.of(this@LoginActivity)[LoginViewModel::class.java]
        switchPassword.isChecked = model.passwordVisible

        switchPassword.setOnClickListener {
            if(model.passwordVisible){
                model.passwordVisible = false
                var start = txtPassword.selectionStart
                var end = txtPassword.selectionEnd
                txtPassword.transformationMethod = PasswordTransformationMethod()
                txtPassword.setSelection(start, end)
            } else {
                model.passwordVisible = true
                var start = txtPassword.selectionStart
                var end = txtPassword.selectionEnd
                txtPassword.transformationMethod = null
                txtPassword.setSelection(start, end)
            }
    }
    }
}


