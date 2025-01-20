package com.github.crisacm.core.data.mapper

import com.github.crisacm.core.database.entity.TaskEntity
import com.github.crisacm.core.domain.model.Task

internal fun Task.asEntity() = TaskEntity(
    id = id,
    title = title,
    dueDate = dueDate,
    isCompleted = isCompleted,
)

internal fun TaskEntity.asDomain() = Task(
    id = id,
    title = title,
    dueDate = dueDate,
    isCompleted = isCompleted,
)
