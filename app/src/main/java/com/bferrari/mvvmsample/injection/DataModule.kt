package com.bferrari.mvvmsample.injection

import com.bferrari.mvvmsample.service.remote.AppApi
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import com.bferrari.mvvmsample.service.repository.ProjectRepository
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesProjectDataSource(api: AppApi): ProjectDataSource = ProjectRepository(api)
}