package com.bferrari.mvvmsample.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bferrari.mvvmsample.ui.projects.ProjectsViewModel
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProjectsViewModel::class)
    abstract fun bindProjectsViewModel(projectsViewModel: ProjectsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}