package io.amntoppo.githubpr.utils

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val finalDate: Date = inputFormat.parse(date) as Date
    return outputFormat.format(finalDate)
}

const val REPOSITORY_NAME = "repository_name"