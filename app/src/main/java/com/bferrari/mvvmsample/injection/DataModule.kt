package com.bferrari.mvvmsample.injection

import android.arch.persistence.room.Room
import android.content.Context
import com.bferrari.mvvmsample.service.local.AppDatabase
import com.bferrari.mvvmsample.service.local.SuggestionDao
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
    fun providesProjectDataSource(api: AppApi,
                                  suggestionDao: SuggestionDao): ProjectDataSource
            = ProjectDataSource(api, suggestionDao)

    @Provides
    @Singleton
    fun providesLocalDatabase(context: Context): AppDatabase
            = Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()

    @Provides
    @Singleton
    fun providesSuggestionDao(database: AppDatabase): SuggestionDao = database.suggestionDao()
}