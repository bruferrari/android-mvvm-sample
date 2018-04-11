package com.bferrari.mvvmsample.service.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Suggestion(
        @PrimaryKey
        var id: Long? = null,
        @ColumnInfo(name = "organization")
        var organization: String) : SearchSuggestion {

    override fun describeContents(): Int = 0

    override fun getBody() = organization
}