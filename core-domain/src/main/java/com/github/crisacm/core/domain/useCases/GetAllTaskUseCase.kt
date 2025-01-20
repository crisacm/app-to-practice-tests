package com.github.crisacm.core.domain.useCases

import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTaskUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> = taskRepository.getAllOnFlow()
}