package com.odella.vetapp.constants

const val BASE_URL = "https://localhost:5001/api/"
const val PREFS_NAME = "VetAppPrefs"
const val PREFS_USERNAME = "username"
const val PREFS_IV = "iv"
const val PREFS_PASSWORD = "password"
const val TRANSFORMATION = "AES/GCM/NoPadding"
const val ANDROID_KEYSTORE = "AndroidKeyStore"
const val DEFAULT_IV = "elodelllajue"
const val SEE_ALL_NAMES = 0
const val SEE_ONLY_PET = 1
const val SEE_ONLY_VET = 2
const val SEE_NOTHING = 3

open class UserSingleton{
    companion object  {
        var actualToken: String = ""
        var userType: String? = null
        var userID: String? = null
    }
}