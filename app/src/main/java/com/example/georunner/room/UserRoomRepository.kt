package com.example.georunner.room

import android.content.Context
import androidx.room.Room
import com.example.georunner.ActivityData

class UserRoomRepository(appContext: Context){
    val db = Room.databaseBuilder(
        appContext,
        UserDatabase::class.java, "database-name"
    ).build()

    val userDao = db.userDao()
    val users: List<User> = userDao.getAll()
    suspend fun updateScore(user: User) {
        userDao.updateUser(user)
    }
    suspend fun updateActivities(userId: Int, activities: List<ActivityData>) {
        userDao.updateActivities(userId, activities)
    }



}
