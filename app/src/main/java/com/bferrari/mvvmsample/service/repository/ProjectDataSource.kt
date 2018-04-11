package com.bferrari.mvvmsample.service.repository

import com.bferrari.mvvmsample.service.local.SuggestionDao
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.model.Suggestion
import com.bferrari.mvvmsample.service.remote.AppApi
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import io.reactivex.Observable
import timber.log.Timber

interface ProjectRepository {
    fun getProjects(organization: String): Observable<List<Project>>
    fun insertLastQuery(suggestion: Suggestion): Observable<Unit>
    fun getLastQueries(): Observable<List<Suggestion>>
}

class ProjectDataSource(private val api: AppApi,
                        private val suggestionDao: SuggestionDao,
                        private val schedulerProvider: SchedulerProviderContract) : ProjectRepository {

    override fun getProjects(organization: String): Observable<List<Project>>
            = api.getRepos(organization)
                .subscribeOn(schedulerProvider.io)
                .doOnNext { it.forEach { Timber.d(it.name) }}

    override fun insertLastQuery(suggestion: Suggestion): Observable<Unit>
            = Observable.fromCallable { suggestionDao.insert(suggestion) }

    override fun getLastQueries(): Observable<List<Suggestion>>
            = Observable.fromCallable { suggestionDao.getAll() }
}