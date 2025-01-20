package com.github.crisacm.core.domain.repository

import com.github.crisacm.core.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun addTask(task: Task): Long

    suspend fun updateTask(task: Task): Int

    suspend fun deleteTask(task: Task): Int

    suspend fun getAll(): List<Task>

    fun getAllOnFlow(): Flow<List<Task>>
}
