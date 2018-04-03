package com.bferrari.mvvmsample.service.model

import com.google.gson.annotations.SerializedName

data class Project(var id: Long,
                   var name: String,
                   var owner: Owner,
                   @SerializedName("html_url")
                      var htmlUrl: String)
