package com.odella.vetapp.modules

import com.odella.vetapp.constants.UserSingleton
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InfoModule {
    @Provides
    @Singleton
    fun getUserSingleton() = UserSingleton()
}