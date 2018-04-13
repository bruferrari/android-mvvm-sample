package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.bferrari.mvvmsample.R
import com.bferrari.mvvmsample.extensions.hide
import com.bferrari.mvvmsample.extensions.openInBrowser
import com.bferrari.mvvmsample.extensions.show
import com.bferrari.mvvmsample.injection.Injectable
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.service.model.Suggestion
import com.bferrari.mvvmsample.util.Status
import com.bferrari.mvvmsample.widgets.GenericAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ProjectsActivity : AppCompatActivity(), Injectable {

    private lateinit var viewModel: ProjectsViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var projectsAdapter: GenericAdapter<Project>

    private var lastQueries: List<Suggestion>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectsViewModel::class.java)

        observeViewModel(viewModel)

        setupRecyclerView()
        setupRefreshingLayout()
        setupSearchWidget()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProjects()
    }

    private fun observeViewModel(viewModel: ProjectsViewModel) {
        viewModel.getProjectsObservable().observe(this, Observer {
            it?.apply {
                when {
                    it.status == Status.SUCCESS -> {
                        setProjects(it.data?.first)
                        lastQueries = it.data?.second
                        handleUiWidgets()
                    }

                    it.status == Status.ERROR -> {
                        displayError(getString(R.string.err_general))
                        handleUiWidgets()
                    }

                    it.status == Status.LOADING -> {
                        progressBar.show()
                    }
                }
            }
        })
    }

    private fun handleUiWidgets() {
        progressBar.hide()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ProjectsActivity, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }

    private fun setupRefreshingLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadProjects()
        }
    }

    private fun setupSearchWidget() {

        floatingSearchView.setOnQueryChangeListener { _, _ ->
            floatingSearchView.swapSuggestions(lastQueries)
        }

        floatingSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {

            override fun onSearchAction(currentQuery: String?) {
                currentQuery?.let { viewModel.storeQuerySuggestion(Suggestion(organization = currentQuery)) }
                viewModel.loadProjects(currentQuery)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) { }
        })
    }

    private fun setProjects(projects: List<Project>?) {
        projectsAdapter = object : GenericAdapter<Project>() {
            override fun getViewHolder(view: View, viewType: Int)
                    = ProjectsViewHolder(view, this@ProjectsActivity::onProjectClick)

            override fun getLayoutId(position: Int, obj: Project) = R.layout.project_item
        }
        recyclerView.adapter = projectsAdapter
        projectsAdapter.setItems(projects ?: listOf())
    }

    fun onProjectClick(project: Project) { openInBrowser(project.htmlUrl) }

    private fun displayError(message: String) {
        Snackbar.make(swipeRefreshLayout, message, Snackbar.LENGTH_LONG).show()
    }
}
