package com.bferrari.mvvmsample.service.remote

import com.bferrari.mvvmsample.service.model.Project
import io.reactivex.Observable
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {

    @GET("orgs/{org}/repos")
    fun getRepos(@Path("org") organization: String): Deferred<List<Project>>

}