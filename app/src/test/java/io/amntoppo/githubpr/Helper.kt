package io.amntoppo.githubpr

import java.io.InputStreamReader

object Helper {
    fun readFileResource(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}
