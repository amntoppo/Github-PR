package io.amntoppo.githubpr.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.amntoppo.githubpr.data.local.GithubDatabase
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.data.repository.GithubRepository
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.time.DurationUnit

@HiltViewModel
class MainViewModel @Inject constructor(
    val pullRequestApi: PullRequestApi,
    val repositoryApi: RepositoryApi,
    val githubDatabase: GithubDatabase,
    val githubRepository: GithubRepository
): ViewModel() {
    private val pullRequestDao = githubDatabase.pullRequestDao()
    private val closedPrMutableData = MutableLiveData<Resource<out List<PullRequest>>>()
    val closedPRData: LiveData<Resource<out List<PullRequest>>> get() = closedPrMutableData

//    val pullRequests = githubRepository.getAllClosedPullRequests()
    fun getAllPullRequest(): LiveData<Resource<out List<PullRequest>>> {
        viewModelScope.launch {
            githubRepository.getAllClosedPullRequests().collect {
                closedPrMutableData.value = it
            }
        }
        return closedPRData
    }

}