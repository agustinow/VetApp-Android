package com.odella.vetapp

import android.app.Application
import com.odella.vetapp.components.DaggerInfoComponent
import com.odella.vetapp.components.InfoComponent

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
        appInjector = DaggerInfoComponent.builder().application(this).build()
    }

    companion object{
        lateinit var instance: App
        lateinit var appInjector: InfoComponent
    }
}