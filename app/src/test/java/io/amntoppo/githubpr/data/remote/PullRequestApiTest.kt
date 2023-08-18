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

class PullRequestApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var pullRequestApi: PullRequestApi

    @Before
    fun before() {
        mockWebServer = MockWebServer()
        pullRequestApi = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PullRequestApi::class.java)
    }

    @Test
    fun pullRequest_ApiTest() = runTest {
        val repositoryMockResponse = MockResponse()
        val repositoryResponse = Helper.readFileResource("pullrequest_response.json")
        repositoryMockResponse.setBody(repositoryResponse)
        repositoryMockResponse.setResponseCode(200)
        mockWebServer.enqueue(repositoryMockResponse)

        val response = pullRequestApi.getClosedPRList("Github-PR")
        mockWebServer.takeRequest(1, TimeUnit.SECONDS)


        Assert.assertEquals(false, response.isEmpty())


    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}