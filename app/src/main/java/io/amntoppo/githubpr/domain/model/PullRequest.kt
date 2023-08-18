package io.amntoppo.githubpr.domain.model

data class PullRequest(
    val body: Any,
    val closed_at: String,
    val created_at: String,
    val id: Int,
    val merged_at: Any,
    val state: String,
    val title: String,
    val updated_at: String,
    val user: User
)