package com.example.georunner.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM Todo")
    fun query(): List<User>

    @Update
    fun update(items: List<User>)

    @Query("DELETE FROM Todo")
    fun deleteAll()

    @Insert
    fun insert(items: List<User>)

}