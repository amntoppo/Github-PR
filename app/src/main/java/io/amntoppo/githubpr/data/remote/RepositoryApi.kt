package io.amntoppo.githubpr.data.remote

import io.amntoppo.githubpr.domain.model.Repository
import retrofit2.http.GET

interface RepositoryApi {

    @GET("users/amntoppo/repos")
    suspend fun getRepoList(): List<Repository>

}