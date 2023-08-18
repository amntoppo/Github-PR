package io.amntoppo.githubpr.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import io.amntoppo.githubpr.domain.model.User
import io.amntoppo.githubpr.utils.JsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromUsertoJson(user: User): String {
        return jsonParser.toJson(
            user,
            User::class.java
        ) ?: "[]"
    }

    @TypeConverter
    fun fromJsontoUser(json: String): User? {
        return jsonParser.fromJson<User>(
            json,
            User::class.java
        )
    }
}