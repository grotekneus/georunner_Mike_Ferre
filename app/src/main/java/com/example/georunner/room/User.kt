package com.example.georunner.room

import androidx.room.*
import com.example.georunner.ActivityData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable
@Serializable
@Entity
data class User(
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "score", defaultValue = "0") var score: Int = 0,
    @ColumnInfo(name = "gamesPlayed", defaultValue = "0") var gamesPlayed: Int = 0,
    @ColumnInfo(name = "timeSpentRunningSeconds", defaultValue = "0") var timeSpentRunningSeconds: Int = 0,
    @ColumnInfo(name = "timeSpentRunningMinutes", defaultValue = "0") var timeSpentRunningMinutes: Int = 0,
    @ColumnInfo(name = "timeSpentRunningHours", defaultValue = "0") var timeSpentRunningHours: Int = 0,
    @ColumnInfo(name = "distanceCovered", defaultValue = "0") var distanceCovered: Int = 0,
    @ColumnInfo(name = "activities") var activities: List<ActivityData> = emptyList(),
    @PrimaryKey(autoGenerate = true) var id: Int = 0) : java.io.Serializable

class Converters {
    @TypeConverter
    fun fromString(value: String): List<ActivityData> {
        return Gson().fromJson(value, object : TypeToken<List<ActivityData>>() {}.type)
    }

    @TypeConverter
    fun fromList(list: List<ActivityData>): String {
        return Gson().toJson(list)
    }
}
