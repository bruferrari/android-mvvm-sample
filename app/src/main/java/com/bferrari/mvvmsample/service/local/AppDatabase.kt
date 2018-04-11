package com.bferrari.mvvmsample.service.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.bferrari.mvvmsample.service.model.Suggestion

@Database(entities = [Suggestion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun suggestionDao(): SuggestionDao
}