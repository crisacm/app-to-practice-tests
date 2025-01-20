package com.github.crisacm.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var title: String,
    var dueDate: Long?,
    var isCompleted: Boolean
)
