package io.amntoppo.githubpr.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.domain.model.PullRequest
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.time.DurationUnit

@HiltViewModel
class MainViewModel @Inject constructor(
    val pullRequestApi: PullRequestApi,
    val repositoryApi: RepositoryApi
): ViewModel() {
    private val closedPrMutableData = MutableLiveData<List<PullRequest>>()
    val closedPRData: LiveData<List<PullRequest>> get() = closedPrMutableData

    fun getAllPullRequest(): LiveData<List<PullRequest>> {
        viewModelScope.launch {
            val PRList = ArrayList<PullRequest>()
            val repoList = repositoryApi.getRepoList()
            withContext(Dispatchers.IO) {
               repoList.map {
                   launch {
                        PRList.addAll(pullRequestApi.getClosedPRList(it.name))
                    }
                }
            }
            closedPrMutableData.value = PRList
        }
        return closedPRData

    }

}