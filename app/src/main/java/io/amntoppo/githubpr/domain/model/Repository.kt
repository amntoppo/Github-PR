package io.amntoppo.githubpr.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Repository")
data class Repository(
    val created_at: String,
    val description: String,
    val full_name: String,
    @PrimaryKey val id: Int,
    val language: String,
    val name: String,
    val updated_at: String,
) : java.io.Serializable
