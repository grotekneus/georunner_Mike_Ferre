package com.example.georunner.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @PrimaryKey(autoGenerate = true) var id: Int = 0) : java.io.Serializable


