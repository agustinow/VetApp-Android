package com.odella.vetapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.afollestad.materialdialogs.MaterialDialog
import com.odella.vetapp.controller.vetFragments.vetConsultFragments.MakeConsultFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.robolectric.Robolectric.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])

class MakeConsultFragmentTest {
    private var subject: MakeConsultFragment? = null
    private var mockAlertDialog: MaterialDialog? = null
    private var activity: Activity? = null

    @Inject
    lateinit var mockContext: Context

    @Before
    fun setup(){
    }

    @Test
    fun fragmentIsDisplayed(){
        val scenario = launchFragmentInContainer<MakeConsultFragment>(
            fragmentArgs = null,
            themeResId = R.style.AppTheme,
            factory = FragmentFactory()
        )



    }
}