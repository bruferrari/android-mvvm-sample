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
    fun getProjects(organization: String): LiveData<List<Project>>
}

class ProjectRepository(private val api: AppApi) : ProjectDataSource {

    companion object {
        const val ORG = "google"
    }

    override fun getProjects(organization: String): LiveData<List<Project>> {
        val data = MutableLiveData<List<Project>>()
        api.getRepos(ORG).map { projects -> data.value = projects }

        return data
    }
}