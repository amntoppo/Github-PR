package io.amntoppo.githubpr.domain.repository

import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.model.Repository
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

     fun getAllClosedPullRequests(): Flow<Resource<out List<PullRequest>>>

//    suspend fun getClosedPullRequest(repo: String): Flow<Resource<List<PullRequest>>>
//
//    suspend fun getRepositoryList(): Flow<Resource<List<Repository>>>
}