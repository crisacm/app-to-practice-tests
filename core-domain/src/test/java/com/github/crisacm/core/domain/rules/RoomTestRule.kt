package com.github.crisacm.core.domain.rules

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.crisacm.core.database.AppDatabase
import com.github.crisacm.core.database.dao.TaskDao
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RoomTestRule : TestRule {
    private lateinit var appDatabase: AppDatabase
    lateinit var taskDao: TaskDao

    override fun apply(
        base: Statement?,
        description: Description?
    ): Statement? {
        return object : Statement() {
            override fun evaluate() {
                val context = ApplicationProvider.getApplicationContext<Context>()
                appDatabase = Room.inMemoryDatabaseBuilder(
                    context,
                    AppDatabase::class.java
                ).allowMainThreadQueries().build()
                taskDao = appDatabase.taskDao()

                try {
                    // Execute the test
                    base?.evaluate()
                } finally {
                    // Clear the database after the test
                    appDatabase.close()
                }
            }
        }
    }
}