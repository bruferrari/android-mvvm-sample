package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.model.Suggestion
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import com.bferrari.mvvmsample.util.Response
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext
import timber.log.Timber
import javax.inject.Inject

interface ProjectsViewModelContract {

    fun getProjectsObservable(): MutableLiveData<Response<Pair<List<Project>, List<Suggestion>>>>
}

class ProjectsViewModel
    @Inject constructor(private val dataSource: ProjectDataSource) : ViewModel(), ProjectsViewModelContract {

    private val data = MutableLiveData<Response<Pair<List<Project>, List<Suggestion>>>>()

    override fun getProjectsObservable() = data

    fun loadProjects(organization: String? = null) {
        data.value = Response.loading()

        async(CommonPool) {
            val result = Pair(dataSource.getProjects(organization ?: "google"),
                    dataSource.getLastQueries())
            try {
                withContext(UI) {
                    data.value = Response.success(Pair(result.first.await(), result.second))
                }
            } catch (e: Exception) {
                Timber.e(e)
                data.value = Response.error(e)
            }
        }
    }

    fun storeQuerySuggestion(suggestion: Suggestion) {
        async { dataSource.insertLastQuery(suggestion) }
    }

}