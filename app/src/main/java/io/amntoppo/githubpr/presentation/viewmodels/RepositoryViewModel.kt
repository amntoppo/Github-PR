package io.amntoppo.githubpr.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.amntoppo.githubpr.data.local.GithubDatabase
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.data.repository.GithubRepository
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.model.Repository
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.DurationUnit

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    val githubRepository: GithubRepository
): ViewModel() {

    //We are using Flows and using it as a LiveData
    val repositoryData = githubRepository.getRepositoryList().asLiveData()
  }