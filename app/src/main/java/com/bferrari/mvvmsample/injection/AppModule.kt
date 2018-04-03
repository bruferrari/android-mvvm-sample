package com.bferrari.mvvmsample.injection

import android.app.Application
import android.content.Context
import com.bferrari.mvvmsample.AppApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(val app: AppApplication) {

    @Provides
    @Singleton
    @Named("ApplicationContext")
    fun providesApplicationContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providesApplication(application: AppApplication): AppApplication = application
}