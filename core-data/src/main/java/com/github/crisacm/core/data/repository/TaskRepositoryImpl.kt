package com.github.crisacm.core.data.repository

import com.github.crisacm.core.data.mapper.asDomain
import com.github.crisacm.core.data.mapper.asEntity
import com.github.crisacm.core.database.dao.TaskDao
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun addTask(task: Task): Long = taskDao.insert(task.asEntity())

    override suspend fun updateTask(task: Task): Int = taskDao.update(task.asEntity())

    override suspend fun deleteTask(task: Task): Int = taskDao.delete(task.id ?: 0)

    override suspend fun getAll(): List<Task> = taskDao.getAll().map { it.asDomain() }

    override fun getAllOnFlow(): Flow<List<Task>> = taskDao.getAllOnFlow().map { it.map { it.asDomain() } }
}
