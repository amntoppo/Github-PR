package io.amntoppo.githubpr.data.repository

import androidx.room.withTransaction
import io.amntoppo.githubpr.data.local.GithubDatabase
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.repository.IGithubRepository
import io.amntoppo.githubpr.utils.NetworkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(
    val repositoryApi: RepositoryApi,
    val pullRequestApi: PullRequestApi,
    var githubDatabase: GithubDatabase
)
    : IGithubRepository {
    private val pullRequestDao = githubDatabase.pullRequestDao()
    private val repositoryDao = githubDatabase.repositoryDao()
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
                pullRequestDao.deleteAllPullRequest()
                pullRequestDao.insertPullRequests(pulls)
            }

        },
        shouldFetch = {true}
    )


    override fun getClosedPullRequest(repo: String) = NetworkBoundResource(
        query = {
            pullRequestDao.getAllClosedPullRequestByRepo(repo)
        },
        fetch = {
            pullRequestApi.getClosedPRList(repo)
        },
        saveRequest = {

        },
        shouldFetch = {true}
    )

    override fun getRepositoryList() = NetworkBoundResource(
        query = {
            repositoryDao.getAllRepository()
        },
        fetch = {
            repositoryApi.getRepoList()
        },
        saveRequest = {
            githubDatabase.withTransaction {
                repositoryDao.deleteAllRepository()
                repositoryDao.insertRepositories(it)
            }
        },
        shouldFetch = {true}
    )
}