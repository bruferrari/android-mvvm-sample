package com.bferrari.mvvmsample.service.repository

import com.bferrari.mvvmsample.service.local.SuggestionDao
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.model.Suggestion
import com.bferrari.mvvmsample.service.remote.AppApi
import kotlinx.coroutines.experimental.Deferred

interface ProjectRepository {
    fun getProjects(organization: String): Deferred<List<Project>>
    fun insertLastQuery(suggestion: Suggestion)
    fun getLastQueries(): List<Suggestion>
}

class ProjectDataSource(private val api: AppApi,
                        private val suggestionDao: SuggestionDao) : ProjectRepository {

    override fun getProjects(organization: String): Deferred<List<Project>>
            = api.getRepos(organization)

    override fun insertLastQuery(suggestion: Suggestion) {
        suggestionDao.insert(suggestion)
    }

    override fun getLastQueries(): List<Suggestion>
            = suggestionDao.getAll()
}