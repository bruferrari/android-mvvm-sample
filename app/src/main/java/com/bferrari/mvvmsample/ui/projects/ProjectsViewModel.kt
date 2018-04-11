package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bferrari.mvvmsample.extensions.addToCompositeDisposable
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.model.Suggestion
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import com.bferrari.mvvmsample.util.Response
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

interface ProjectsViewModelContract {

    fun unsubscribe()

    fun getProjectsObservable(): MutableLiveData<Response<Pair<List<Project>, List<Suggestion>>>>
}

class ProjectsViewModel
    @Inject constructor(private val dataSource: ProjectDataSource) : ViewModel(), ProjectsViewModelContract {

    private val compositeDisposable = CompositeDisposable()
    @Inject lateinit var schedulerProvider: SchedulerProviderContract

    private val data = MutableLiveData<Response<Pair<List<Project>, List<Suggestion>>>>()

    override fun unsubscribe() {
        compositeDisposable.dispose()
    }

    override fun getProjectsObservable() = data

    fun loadProjects(organization: String? = null) {
        Observable.zip(dataSource.getProjects(organization ?: "google"),  dataSource.getLastQueries(),
                BiFunction { projects: List<Project>, suggestions: List<Suggestion> ->
                    Pair(projects, suggestions)
                })
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .doOnSubscribe { data.value = Response.loading() }
                .subscribe ({
                    data.value = Response.success(it)
                }, {
                    Timber.e(it)
                    data.value = Response.error(it)
                }).addToCompositeDisposable(compositeDisposable)
    }

    fun storeQuerySuggestion(suggestion: Suggestion) {
        dataSource.insertLastQuery(suggestion)
                .subscribeOn(schedulerProvider.io)
                .subscribe()
                .addToCompositeDisposable(compositeDisposable)
    }

}