package com.example.georunner.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            //if(tempInstance != null){
              //  return tempInstance
            //}
            synchronized(this){
                //context.deleteDatabase("user_database")

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                    //.fallbackToDestructiveMigration()
                    //.build()
                return instance
            }
        }
    }
}

