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
    @PrimaryKey(autoGenerate = true) var id: Int = 0) : java.io.Serializable


