package com.odella.vetapp.constants

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

import java.text.SimpleDateFormat
import java.util.*

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
const val CONSULT_ID="consultid"

//THIS IS AN EASTER EGG HUEHUEHUE
const val STATUS_UNFINISHED = 0
const val STATUS_ERROR = 2
const val STATUS_FINISHED = 1

open class UserSingleton{
    companion object  {
        var actualToken: String = ""
        var userType: String? = null
        var userID: String? = null
    }
}
fun writeEmail(recipient:String,subject:String,message:String): Intent{
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL,recipient)
    intent.putExtra(Intent.EXTRA_SUBJECT,subject)
    intent.putExtra(Intent.EXTRA_TEXT,message)
    return intent
}


fun formatDate(date: Date): String = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(date)