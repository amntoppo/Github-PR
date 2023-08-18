package io.amntoppo.githubpr.presentation.listeners

import io.amntoppo.githubpr.domain.model.Repository

interface RepositoryOnClickListener {
    fun onRepositoryClick(modelItem: Repository);
}