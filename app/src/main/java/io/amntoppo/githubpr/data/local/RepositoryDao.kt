package io.amntoppo.githubpr.data.local

import androidx.room.Dao
import androidx.room.Query
import io.amntoppo.githubpr.domain.model.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM Repository")
    fun getAllRepository(): Flow<List<Repository>>
}