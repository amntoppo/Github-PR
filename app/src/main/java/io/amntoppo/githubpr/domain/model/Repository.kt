package io.amntoppo.githubpr.domain.model

data class Repository(
    val created_at: String,
    val description: String,
    val full_name: String,
    val id: Int,
    val language: String,
    val name: String,
    val updated_at: String,
)
