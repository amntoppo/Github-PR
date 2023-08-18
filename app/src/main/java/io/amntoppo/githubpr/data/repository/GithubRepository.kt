package io.amntoppo.githubpr.data.repository

import androidx.room.withTransaction
import io.amntoppo.githubpr.data.local.GithubDatabase
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.model.Repository
import io.amntoppo.githubpr.domain.repository.IGithubRepository
import io.amntoppo.githubpr.utils.NetworkBoundResource
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(
    val repositoryApi: RepositoryApi,
    val pullRequestApi: PullRequestApi,
    var githubDatabase: GithubDatabase
)
    : IGithubRepository {
    private val pullRequestDao = githubDatabase.pullRequestDao()
    override fun getAllClosedPullRequests() = NetworkBoundResource(
        query = {
            pullRequestDao.getAllClosedPullRequest()
        },
        fetch = {
            var repoList = repositoryApi.getRepoList()
            var prList = ArrayList<PullRequest>()
            repoList.map {
                withContext(Dispatchers.IO) {
                    var prs = async {
                        pullRequestApi.getClosedPRList(it.name)
                    }
                    prList.addAll(prs.await())
                }

            }
            prList
        },
        saveRequest = { pulls ->
            githubDatabase.withTransaction {
                pullRequestDao.delectAllPullRequest()
                pullRequestDao.insertPullRequests(pulls)
            }

        },
        shouldFetch = {true}
    )


//    : Flow<Resource<List<PullRequest>>> {
//        val PRList = ArrayList<PullRequest>()
//        val repoList = repositoryApi.getRepoList()
//        withContext(Dispatchers.IO) {
//            repoList.map {
//                launch {
//                    PRList.addAll(pullRequestApi.getClosedPRList(it.name))
//                }
//            }
//        }
//    }

//    override suspend fun getClosedPullRequest(repo: String): Flow<Resource<List<PullRequest>>> {
//        return null
////        TODO("Not yet implemented")
//    }
//
//    override suspend fun getRepositoryList(): Flow<Resource<List<Repository>>> {
////        TODO("Not yet implemented")
//    }
}