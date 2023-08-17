package io.amntoppo.githubpr.data.remote

import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.model.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {


    @GET("users/amntoppo/repos")
    suspend fun getRepositories(): List<Repository>

    @GET("/repos/amntoppo/{repositoryName}/pulls?state=closed")
    suspend fun getPullRequests(@Path("repositoryName") repositoryName: String): List<PullRequest>
}