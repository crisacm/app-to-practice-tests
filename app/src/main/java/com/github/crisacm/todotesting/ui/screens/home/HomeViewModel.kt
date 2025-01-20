package com.github.crisacm.todotesting.ui.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.useCases.AddNewTaskUseCase
import com.github.crisacm.core.domain.useCases.DeleteTaskUseCase
import com.github.crisacm.core.domain.useCases.EditTaskUseCase
import com.github.crisacm.core.domain.useCases.GetAllTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTaskUseCase,
    private val addNewTaskUseCase: AddNewTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    val tasks: MutableLiveData<List<Task>> by lazy { MutableLiveData<List<Task>>() }

    fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTasksUseCase().collectLatest {
                tasks.postValue(it)
            }
        }
    }

    @Suppress("unused")
    fun addTestingTask() {
        viewModelScope.launch(Dispatchers.IO) {
            addNewTaskUseCase(
                Task(
                    id = null,
                    title = "Testing Task",
                    dueDate = null,
                    isCompleted = false
                )
            )
        }
    }

    fun addTask(
        title: String,
        date: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewTaskUseCase(
                Task(
                    id = null,
                    title = title,
                    dueDate = date,
                    isCompleted = false
                )
            )
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            editTaskUseCase(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(task)
        }
    }
}