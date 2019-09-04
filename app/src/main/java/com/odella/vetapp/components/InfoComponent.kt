package com.odella.vetapp.components

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.odella.vetapp.constants.UserSingleton
import com.odella.vetapp.controller.LoginActivity
import com.odella.vetapp.controller.VetActivity
import com.odella.vetapp.controller.vetFragments.VetViewModel
import com.odella.vetapp.modules.InfoModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InfoModule::class])
interface InfoComponent {

    fun inject(subject: LoginActivity)
    fun inject(subject: VetActivity)
    fun inject(subject: VetViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): InfoComponent
    }
}