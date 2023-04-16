package com.example.georunner.room

import android.content.Context
import androidx.room.Room

class UserRoomRepository(appContext: Context){
    val db = Room.databaseBuilder(
        appContext,
        UserDatabase::class.java, "database-name"
    ).build()

    val userDao = db.userDao()
    val users: List<User> = userDao.getAll()

    //fun getAllUsers(): List<User> {
      //  return userDao.getAll()
    //}

    //fun insertUser(user: User) {
      //  userDao.insert(user)
    //}


}

/*
class TodoRoomRepository(appContext: Context) : TodoRepository {

    private val db: TodoDatabase
    private val dao: TodoDao

    init {
        db = Room.databaseBuilder(appContext, TodoDatabase::class.java, "todo-db")
            .allowMainThreadQueries()
            .build()
        dao = db.todoDao()
    }

    override fun load(): List<Todo> = dao.query()

    override fun save(items: List<Todo>) {
        // You'll learn more about transactions in the database course in the 3rd academic year.
        db.runInTransaction {
            dao.deleteAll()
            dao.insert(items)
        }
    }

}
 */