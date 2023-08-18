package io.amntoppo.githubpr.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.amntoppo.githubpr.data.local.GithubDatabase
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GithubRepositoryTest {

    @Mock
    lateinit var pullRequestApi: PullRequestApi
    @Mock lateinit var repositoryApi: RepositoryApi
    @Mock lateinit var githubDatabase: GithubDatabase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()
        githubDatabase = Room.inMemoryDatabaseBuilder(
            context, GithubDatabase::class.java).build()
    }

    @Test
    fun getPullRequest_EmptyList() = runTest{
        Mockito.`when`(pullRequestApi.getClosedPRList("")).thenReturn(emptyList())
        Mockito.`when`(repositoryApi.getRepoList()).thenReturn(emptyList())

        val repo = GithubRepository(repositoryApi, pullRequestApi, githubDatabase)
        val result = repo.getAllClosedPullRequests()
        result.collect {
            Assert.assertEquals(true, it is Resource.Success)
            Assert.assertEquals(0, it.data!!.size)

        }
    }



    @After
    fun tearDown() {
    }
}