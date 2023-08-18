package io.amntoppo.githubpr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.domain.model.Repository

@Database(entities = [PullRequest::class, Repository::class], version = 1)
@TypeConverters(Converters::class)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun pullRequestDao(): PullRequestDao
    abstract fun repositoryDao(): RepositoryDao
}