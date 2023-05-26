package com.example.georunner
import java.io.Serializable

data class ActivityData(
    val gameNr: Int,
    val distance: Int,
    val timeHours: Int,
    val timeMinutes: Int,
    val timeSeconds: Int
    ):Serializable
