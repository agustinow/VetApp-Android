package com.odella.vetapp.controller

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProviders
import com.odella.vetapp.R
import com.odella.vetapp.constants.*
import com.odella.vetapp.service.NetworkService
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import java.security.SecureRandom
import javax.crypto.spec.IvParameterSpec


class LoginActivity : AppCompatActivity() {
    lateinit var model: LoginViewModel


    @ExperimentalStdlibApi
    private fun encryptAndSave(userIn: String){

        if(userIn.isNotEmpty()) {

            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
            keyStore.load(null)

            if (!keyStore.containsAlias(PREFS_USERNAME)){
                val keyGenerator =
                    KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
                val keySpecs = KeyGenParameterSpec.Builder(
                    PREFS_USERNAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
                keyGenerator.init(keySpecs)
                keyGenerator.generateKey()
            }

            val cipher = Cipher.getInstance(TRANSFORMATION)
            /*
            val iv = ByteArray(12)
            val e = SecureRandom()
            e.nextBytes(iv)
            */

            val key = keyStore.getKey(PREFS_USERNAME, null)
            //val gcmParams = GCMParameterSpec(128, iv)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val bytesOfUser = userIn.encodeToByteArray()
            var encodedBytes = cipher.doFinal(bytesOfUser)
            var encryptedEncodedBytes = Base64.encodeToString(encodedBytes, Base64.NO_PADDING)


            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_USERNAME, encryptedEncodedBytes)
                .putString(PREFS_IV, Base64.encodeToString(cipher.iv
                    , Base64.DEFAULT))
                .apply()
        } else{
            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_USERNAME, "")
                .putString(PREFS_IV, "")
                .apply()
        }

    }

    @ExperimentalStdlibApi
    private fun decryptUsername() : String{
        val iv = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(PREFS_IV, "")
        val encrypted = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(PREFS_USERNAME, "")

        if(encrypted.isNullOrEmpty()) return ""

        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        if(!keyStore.containsAlias(PREFS_USERNAME)) return ""

        val key = keyStore.getKey(PREFS_USERNAME, null)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT)))
        val decodedBytes = cipher.doFinal(Base64.decode(encrypted, Base64.NO_PADDING))
        return decodedBytes.decodeToString()
/*
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        val secretKeyEntry = keyStore.getEntry(PREFS_USERNAME, null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT))
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val username = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
        return Base64.encodeToString(username, Base64.DEFAULT)
 */
    }


    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        model = ViewModelProviders.of(this@LoginActivity)[LoginViewModel::class.java]

        //val username = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(PREFS_USERNAME, "")
        val username = decryptUsername()


        if(username.isNullOrEmpty()){
            model.rememberMe = false
            chkRememberMe.isChecked = false
        } else {
            model.rememberMe = true
            chkRememberMe.isChecked = true
            txtUsername.setText(username)
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

    @ExperimentalStdlibApi
    fun tryToConnect(username:String, password:String){
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
                    makeText(this@LoginActivity, "Invalid credentials", LENGTH_SHORT).show()
                    txtUsername.setText("")
                    txtPassword.setText("")
                }
            }

        })

    }

    @ExperimentalStdlibApi
    fun login(){
        if(chkRememberMe.isChecked) {
            encryptAndSave(txtUsername.text.toString())
        } else {
            encryptAndSave("")
        }
        val intent = Intent(this@LoginActivity, VetActivity::class.java)
        startActivity(intent)
    }
}



