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
import io.amntoppo.githubpr.domain.model.Repository
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
    private val repoPrMutableData = MutableLiveData<Resource<out List<PullRequest>>>()
    val repoPRData: LiveData<Resource<out List<PullRequest>>> get() = closedPrMutableData
    private val repositoryLiveData = MutableLiveData<Resource<out List<Repository>>>()
    val repositoryData: LiveData<Resource<out List<Repository>>> get() = repositoryLiveData

//    val pullRequests = githubRepository.getAllClosedPullRequests()
    fun getAllPullRequest(): LiveData<Resource<out List<PullRequest>>> {
        viewModelScope.launch {
            githubRepository.getAllClosedPullRequests().collect {
                closedPrMutableData.value = it
            }
        }
        return closedPRData
    }

    fun getAllRepository(): LiveData<Resource<out List<Repository>>> {
        viewModelScope.launch {
            githubRepository.getRepositoryList().collect {
                repositoryLiveData.value = it
            }
        }
        return repositoryData
    }

    fun getClosedPullRequests(repository: String): LiveData<Resource<out List<PullRequest>>> {
        viewModelScope.launch {
            githubRepository.getClosedPullRequest(repository).collect {
                repoPrMutableData.value = it
            }
        }
        return repoPRData
    }

}