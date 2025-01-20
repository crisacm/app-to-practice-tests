package com.github.crisacm.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.crisacm.core.database.dao.TaskDao
import com.github.crisacm.core.database.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
