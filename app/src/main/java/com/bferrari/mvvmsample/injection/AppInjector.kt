package com.bferrari.mvvmsample.injection

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.bferrari.mvvmsample.AppApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 */
object AppInjector {
    fun init(appApplication: AppApplication) {
        DaggerAppComponent.builder().application(appApplication)
                .build().inject(appApplication)
        appApplication
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        Timber.d("activity Created")
                        handleActivity(activity)
                    }

                    override fun onActivityStarted(activity: Activity) {

                    }

                    override fun onActivityResumed(activity: Activity) {

                    }

                    override fun onActivityPaused(activity: Activity) {

                    }

                    override fun onActivityStopped(activity: Activity) {

                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                    }

                    override fun onActivityDestroyed(activity: Activity) {

                    }
                })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            Timber.d("FragmentActivity")

            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?,
                                                               savedInstanceState: Bundle?) {
                                    Timber.d("FragmentActivity injectable")

                                    if (f is Injectable) {
                                        Timber.d("FragmentActivity injectable")
                                        AndroidSupportInjection.inject(f)
                                    }
                                }
                            }, true)
        }
    }
}