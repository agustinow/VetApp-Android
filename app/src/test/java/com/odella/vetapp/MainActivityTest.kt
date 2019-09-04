package com.odella.vetapp

import android.content.Intent
import android.widget.ImageView
import com.odella.vetapp.controller.MainActivity
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.odella.vetapp.controller.LoginActivity
import com.odella.vetapp.service.NetworkService
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.shadow.api.Shadow
import retrofit2.Call

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class MainActivityTest {
    private var subject: MainActivity? = null

    @Before
    fun setup() {
        subject = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

    }

    @Test
    fun activityExists() {
        assertThat(subject).isNotNull()
    }

    @Test
    fun imageLoaded() {
        assertThat(subject!!.findViewById<ImageView>(R.id.imgLogo)!!.background).isNotNull()
    }

    @Test
    fun goToLogin() {
        subject!!.login()
        val expectedIntent = Intent(subject, LoginActivity::class.java)
        val actual = Shadows.shadowOf(subject).nextStartedActivity
        assertThat(expectedIntent.component).isEqualTo(actual.component)
    }
}