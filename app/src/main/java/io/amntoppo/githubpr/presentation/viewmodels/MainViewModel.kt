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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val pullRequestApi: PullRequestApi,
    val repositoryApi: RepositoryApi
): ViewModel() {
    private val closedPrMutableData = MutableLiveData<List<PullRequest>>()
    public val closedPRData: LiveData<List<PullRequest>> get() = closedPrMutableData

    fun getClosedPR() {
        viewModelScope.launch {
            var res = pullRequestApi.getClosedPRList("Github-PR")
            Log.e("pull", res.toString())
        }
    }

    fun getAllPullRequest() {
        viewModelScope.launch {
            var PRList = ArrayList<PullRequest>()
            repositoryApi.getRepoList().forEach {
//                PRList.addAll(pullRequestApi.getClosedPRList(it.name))
                closedPrMutableData.value = pullRequestApi.getClosedPRList(it.name)
            }

        }
    }
}