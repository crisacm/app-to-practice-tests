package com.github.crisacm.core.domain.useCases

import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository

class EditTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }
}