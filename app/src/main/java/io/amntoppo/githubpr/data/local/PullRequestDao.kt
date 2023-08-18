package io.amntoppo.githubpr.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import io.amntoppo.githubpr.domain.model.PullRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface PullRequestDao {

    @Query("SELECT * FROM PullRequest WHERE repoName LIKE '%' || :repoName || '%'")
    fun getAllClosedPullRequest(repoName: String): Flow<List<PullRequest>>

    @Insert(onConflict = REPLACE)
    suspend fun insertPullRequests(pullRequests: List<PullRequest>)

    @Query("DELETE FROM PullRequest")
    suspend fun delectAllPullRequest()
}