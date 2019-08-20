package com.odella.vetapp.constants

const val BASE_URL = "https://localhost:5001/api/"
const val PREFS_NAME = "VetAppPrefs"
const val PREFS_USERNAME = "username"
const val PREFS_PASSWORD = "password"

open class TokenSingleton{
    companion object  {
        var actualToken: String = ""
    }
}