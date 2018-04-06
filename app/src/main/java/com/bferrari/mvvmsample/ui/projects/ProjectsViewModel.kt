package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import javax.inject.Inject

class ProjectsViewModel
    @Inject constructor(private val dataSource: ProjectDataSource) : ViewModel() {

    private var projectsObservable: LiveData<List<Project>> = dataSource.getProjects("google")

    fun getProjectsObservable(): LiveData<List<Project>> = projectsObservable
}