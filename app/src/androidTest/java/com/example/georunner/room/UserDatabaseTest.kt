package com.example.georunner.room

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserDatabaseTest{

    private lateinit var db: UserDatabase
    private lateinit var dao: UserDao

    @Before
    public fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, UserDatabase::class.java)
            .build()
        db.clearAllTables()
        dao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testDataBase()= runBlocking {
        val item = User("bru", "k")
        dao.insert(item)

        val refreshedItem = dao.query().single()
        with(refreshedItem) {
            TestCase.assertEquals(item.userName, "k")
            TestCase.assertEquals(item.password, "bru")
            TestCase.assertEquals(1, id)
        }
    }
}