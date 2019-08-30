package com.odella.vetapp

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
import org.junit.Test
import org.robolectric.Shadows
import org.robolectric.shadow.api.Shadow

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

    }
}