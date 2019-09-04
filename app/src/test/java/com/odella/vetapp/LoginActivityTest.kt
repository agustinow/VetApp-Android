package com.odella.vetapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.CheckBox
import androidx.test.platform.app.InstrumentationRegistry
import com.odella.vetapp.constants.PREFS_USERNAME
import com.odella.vetapp.controller.LoginActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import com.odella.vetapp.constants.PREFS_IV
import com.odella.vetapp.constants.UserSingleton
import dagger.android.DaggerActivity
import dagger.android.DaggerActivity_MembersInjector
import dagger.android.DaggerApplication
import dagger.android.DaggerApplication_MembersInjector
import kotlinx.android.synthetic.main.activity_login.*
import org.mockito.*
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import java.security.KeyStore
import kotlin.text.Typography.dagger


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class LoginActivityTest {
    lateinit var subject: LoginActivity
    @Mock
    lateinit var mockContext: Context
    @Mock
    lateinit var mockSharedPrefs: SharedPreferences
    @Mock
    lateinit var mockEditor: SharedPreferences.Editor
    @Mock
    var mockUserSingleton = UserSingleton()

    @ExperimentalStdlibApi
    @Before
    fun setup() {
        subject = Robolectric.buildActivity(LoginActivity::class.java)
            .create()
            .resume()
            .get()
        subject.userSingleton.userType = "vet"
        subject.userSingleton.actualToken = "Bearer reloco"
        subject.userSingleton.userID = "TremendaID"
    }

    @ExperimentalStdlibApi
    //@Test
    fun check(){
       // assertThat(subject.userSingleton.actualToken).isEqualTo("Bearer reloco")
    }

    //@Test
    fun checkEncrypt(){

    }
}