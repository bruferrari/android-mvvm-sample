package com.bferrari.mvvmsample.ui.projects

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bferrari.mvvmsample.R
import com.bferrari.mvvmsample.injection.Injectable
import com.bferrari.mvvmsample.service.model.Project
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class ProjectsActivity : AppCompatActivity(), Injectable {

    private lateinit var viewModel: ProjectsViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectsViewModel::class.java)

        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: ProjectsViewModel) {
        viewModel.getProjectsObservable().observe(this, Observer<List<Project>> {
            it?.let {
                Timber.d(it.toString())
            }
        })
    }
}
