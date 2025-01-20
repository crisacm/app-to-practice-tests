package com.github.crisacm.core.domain.model

data class Task(
    var id: Long?,
    var title: String,
    var dueDate: Long?,
    var isCompleted: Boolean
)
