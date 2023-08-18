package io.amntoppo.githubpr.data.remote

import io.amntoppo.githubpr.domain.model.PullRequest
import retrofit2.http.GET
import retrofit2.http.Path

interface PullRequestApi {

    @GET("/repos/amntoppo/{repositoryName}/pulls?state=closed")
    suspend fun getClosedPRList(@Path("repositoryName") repositoryName: String): List<PullRequest>
}