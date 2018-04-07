package com.bferrari.mvvmsample.service.repository

import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.remote.AppApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

interface ProjectDataSource {
    fun getProjects(organization: String): Observable<List<Project>>
}

class ProjectRepository(private val api: AppApi) : ProjectDataSource {

    companion object {
        const val ORG = "google"
    }

    override fun getProjects(organization: String): Observable<List<Project>>
            = api.getRepos(ORG)
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    Timber.d("passing trough getRepos")
                    it.forEach { Timber.d(it.name) }}
}