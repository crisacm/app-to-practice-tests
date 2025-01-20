package com.github.crisacm.core.data.di

import com.github.crisacm.core.data.repository.TaskRepositoryImpl
import com.github.crisacm.core.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
}
