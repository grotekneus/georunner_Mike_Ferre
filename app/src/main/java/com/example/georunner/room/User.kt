package com.example.georunner.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
@Serializable
@Entity
data class User(
    @ColumnInfo(name = "password") val password: String,
    @PrimaryKey(autoGenerate = false) var userName: String = "") : java.io.Serializable

