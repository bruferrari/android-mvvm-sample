package com.bferrari.mvvmsample

import android.app.Application
import android.app.Activity
import com.bferrari.mvvmsample.injection.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


open class AppApplication : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? = dispatchingAndroidInjector
}