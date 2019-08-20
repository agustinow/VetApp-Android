package com.odella.vetapp.controller

import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {
    var rememberMe = false
    var passwordVisible = false
    var username = ""
    var password = ""
}