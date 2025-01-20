package com.github.crisacm.core.domain.di

import com.github.crisacm.core.domain.repository.TaskRepository
import com.github.crisacm.core.domain.useCases.AddNewTaskUseCase
import com.github.crisacm.core.domain.useCases.DeleteTaskUseCase
import com.github.crisacm.core.domain.useCases.EditTaskUseCase
import com.github.crisacm.core.domain.useCases.GetAllTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun getAllTaskUseCaseProvider(
        taskRepository: TaskRepository
    ): GetAllTaskUseCase {
        return GetAllTaskUseCase(taskRepository)
    }

    @Provides
    fun addNewTaskUseCaseProvider(
        taskRepository: TaskRepository
    ): AddNewTaskUseCase {
        return AddNewTaskUseCase(taskRepository)
    }

    @Provides
    fun deleteTaskUseCaseProvider(
        taskRepository: TaskRepository
    ): DeleteTaskUseCase {
        return DeleteTaskUseCase(taskRepository)
    }

    @Provides
    fun editTaskUseCaseProvider(
        taskRepository: TaskRepository
    ): EditTaskUseCase {
        return EditTaskUseCase(taskRepository)
    }
}