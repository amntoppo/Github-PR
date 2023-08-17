package io.amntoppo.githubpr.data.remote

import io.amntoppo.githubpr.BuildConfig
import io.amntoppo.githubpr.Helper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RepositoryApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var repositoryApi: RepositoryApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        repositoryApi = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RepositoryApi::class.java)
        }

    @Test
    fun testGetRepository() = runTest {
        val repositoryMockResponse = MockResponse()
        val repositoryResponse = Helper.readFileResource("repository_response.json")
        repositoryMockResponse.setBody(repositoryResponse)
        repositoryMockResponse.setResponseCode(200)
        mockWebServer.enqueue(repositoryMockResponse)

        val response = repositoryApi.getRepoList()
        mockWebServer.takeRequest(1, TimeUnit.SECONDS)


        Assert.assertEquals(125470555, response.get(0).id)
        Assert.assertEquals(false, response.isEmpty())
        Assert.assertEquals(false, response.get(0).name.isEmpty())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}