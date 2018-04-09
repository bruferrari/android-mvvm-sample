package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bferrari.mvvmsample.extensions.addToCompositeDisposable
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import com.bferrari.mvvmsample.util.SchedulerProvider
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ProjectsViewModelContract {
    fun getProjectsObservable(): LiveData<List<Project>>
    fun unsubscribe()
}

class ProjectsViewModel
    @Inject constructor(private val dataSource: ProjectDataSource) : ViewModel(), ProjectsViewModelContract {

    private val compositeDisposable = CompositeDisposable()
    @Inject lateinit var schedulerProvider: SchedulerProviderContract

    private val data = MutableLiveData<List<Project>>()

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    fun loadProjects() {
        dataSource.getProjects("google")
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe { data.value = it }
                .addToCompositeDisposable(compositeDisposable)
    }

    override fun getProjectsObservable() = data
}