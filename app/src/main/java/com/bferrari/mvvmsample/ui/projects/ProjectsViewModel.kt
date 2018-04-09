package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bferrari.mvvmsample.extensions.addToCompositeDisposable
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import com.bferrari.mvvmsample.util.Response
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

interface ProjectsViewModelContract {

    fun unsubscribe()

    fun getProjectsObservable(): MutableLiveData<Response>
}

class ProjectsViewModel
    @Inject constructor(private val dataSource: ProjectDataSource) : ViewModel(), ProjectsViewModelContract {

    private val compositeDisposable = CompositeDisposable()
    @Inject lateinit var schedulerProvider: SchedulerProviderContract

    private val data = MutableLiveData<Response>()

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    fun loadProjects() {
        dataSource.getProjects("google")
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

    override fun getProjectsObservable() = data
}