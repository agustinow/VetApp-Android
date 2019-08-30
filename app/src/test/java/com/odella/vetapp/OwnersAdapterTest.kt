package com.odella.vetapp

import com.odella.vetapp.adapters.UsersAdapter
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])

class OwnersAdapterTest {
    lateinit var subject: UsersAdapter

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }
}