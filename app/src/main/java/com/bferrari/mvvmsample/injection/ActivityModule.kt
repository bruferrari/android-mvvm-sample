package com.bferrari.mvvmsample.injection

import com.bferrari.mvvmsample.ui.projects.ProjectsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeProjectsActivity(): ProjectsActivity
}