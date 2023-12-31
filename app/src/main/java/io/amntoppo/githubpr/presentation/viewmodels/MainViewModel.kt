package io.amntoppo.githubpr.presentation.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.amntoppo.githubpr.data.repository.GithubRepository
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.repository.IGithubRepository
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val githubRepository: IGithubRepository
): ViewModel() {
    private val _pullRequestStateFlow = MutableStateFlow<Resource<out List<PullRequest>>>(Resource.Loading(null))
    val pullRequestStateFlow = _pullRequestStateFlow.asStateFlow()
    var allPullRequestData = githubRepository.getAllClosedPullRequests().asLiveData()


    //Demonstration of StateFlow as a State holder, substitute of LiveData
    fun getPullRequest(repo: String) {
        if(repo.isEmpty()) {
            _pullRequestStateFlow.value = Resource.Error(Throwable("The repository name is Empty"))
        }
        viewModelScope.launch {
            githubRepository.getClosedPullRequest(repo).collect {
                _pullRequestStateFlow.value = it
            }
        }
    }

}