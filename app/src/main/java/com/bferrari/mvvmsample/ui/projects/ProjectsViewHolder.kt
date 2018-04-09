package com.bferrari.mvvmsample.ui.projects

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bferrari.mvvmsample.service.model.Project
import com.bferrari.mvvmsample.widgets.GenericAdapter
import kotlinx.android.synthetic.main.project_item.view.*

class ProjectsViewHolder(val view: View, private val clickListener: (Project) -> Unit)
    : RecyclerView.ViewHolder(view), GenericAdapter.Binder<Project> {

    override fun bind(project: Project, position: Int) {
        with(view) {
            txtId.text = project.id.toString()
            name.text = project.name
            owner.text = project.owner.fullName

            setOnClickListener{ clickListener.invoke(project) }
        }
    }
}