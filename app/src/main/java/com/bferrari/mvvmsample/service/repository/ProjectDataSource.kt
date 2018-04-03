package com.bferrari.mvvmsample.service.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.bferrari.mvvmsample.extensions.addToCompositeDisposable
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.remote.AppApi
import com.bferrari.mvvmsample.util.SchedulerProviderContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface ProjectDataSource {
    fun unsubscribe()
    fun getProjects(organization: String): LiveData<List<Project>>
}

class ProjectRepository(val api: AppApi,
                        val schedulerProvider: SchedulerProviderContract) : ProjectDataSource {

    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val ORG = "google"
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun getProjects(organization: String): LiveData<List<Project>> {
        val data = MutableLiveData<List<Project>>()

        api.getRepos(ORG)
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe ({
                    data.value = it
                }, {
                    Timber.d(it)
                })
                .addToCompositeDisposable(compositeDisposable)

        return data
    }
}