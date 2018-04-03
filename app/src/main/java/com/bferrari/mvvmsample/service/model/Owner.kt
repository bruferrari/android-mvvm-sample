package com.bferrari.mvvmsample.service.model

import com.google.gson.annotations.SerializedName

data class Owner(var id: Long,
                 @SerializedName("full_name")
                 var fullName: String )