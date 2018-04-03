package com.bferrari.mvvmsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.repository.ProjectDataSource
import javax.inject.Inject

class ProjectsViewModel
    @Inject constructor(dataSource: ProjectDataSource) : AndroidViewModel(Application()) {

    private var projectsObservable: LiveData<List<Project>> = dataSource.getProjects("google")

    fun getProjectsObservable(): LiveData<List<Project>> = projectsObservable
}