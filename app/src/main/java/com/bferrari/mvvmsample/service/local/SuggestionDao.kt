package com.bferrari.mvvmsample.service.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.bferrari.mvvmsample.service.model.Suggestion

@Dao
interface SuggestionDao {

    @Query("select * from suggestion")
    fun getAll(): List<Suggestion>

    @Insert
    fun insert(suggestion: Suggestion)

    @Delete
    fun delete(query: Suggestion)
}