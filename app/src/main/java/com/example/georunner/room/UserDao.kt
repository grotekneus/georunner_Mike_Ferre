package com.example.georunner.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun query(): List<User>

    @Update
    fun update(items: List<User>)

    @Update
    fun updateUser(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()

    @Query("SELECT * FROM User")
    fun getAll(): List<User>


    @Insert
    fun insert(item: User)

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsername(username: String): User


}