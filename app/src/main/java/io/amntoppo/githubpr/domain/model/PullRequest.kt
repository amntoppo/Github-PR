package io.amntoppo.githubpr.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PullRequest")
data class PullRequest(
    val closed_at: String,
    val created_at: String,
    @PrimaryKey val id: Int,
    val state: String,
    val title: String,
    val updated_at: String,
    val user: User,
    var repoName: String?
) : java.io.Serializable