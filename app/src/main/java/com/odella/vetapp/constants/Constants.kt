package com.odella.vetapp.constants

const val BASE_URL = "https://localhost:5001/api/"


open class TokenSingleton{
    companion object  {
        var actualToken: String = ""
    }
}