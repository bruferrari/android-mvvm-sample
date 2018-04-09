package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bferrari.mvvmsample.R
import com.bferrari.mvvmsample.extensions.hide
import com.bferrari.mvvmsample.extensions.openInBrowser
import com.bferrari.mvvmsample.injection.Injectable
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.widgets.GenericAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ProjectsActivity : AppCompatActivity(), Injectable {

    private lateinit var viewModel: ProjectsViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var projectsAdapter: GenericAdapter<Project>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectsViewModel::class.java)

        viewModel.loadProjects()

        observeViewModel(viewModel)

        setupRecyclerView()
    }

    private fun observeViewModel(viewModel: ProjectsViewModel) {
        viewModel.getProjectsObservable().observe(this, Observer {
            it?.let {
                setProjects(it)
                progressBar.hide()
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ProjectsActivity, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }

    fun setProjects(projects: List<Project>) {
        projectsAdapter = object : GenericAdapter<Project>() {
            override fun getViewHolder(view: View, viewType: Int)
                    = ProjectsViewHolder(view, this@ProjectsActivity::onProjectClick)

            override fun getLayoutId(position: Int, obj: Project) = R.layout.project_item
        }
        recyclerView.adapter = projectsAdapter
        projectsAdapter.setItems(projects)
    }

    fun onProjectClick(project: Project) { openInBrowser(project.htmlUrl) }
}
