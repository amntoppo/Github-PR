package io.amntoppo.githubpr.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.amntoppo.githubpr.domain.model.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM Repository")
    fun getAllRepository(): Flow<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(pullRequests: List<Repository>)

    @Query("DELETE FROM Repository")
    suspend fun deleteAllRepository()
}