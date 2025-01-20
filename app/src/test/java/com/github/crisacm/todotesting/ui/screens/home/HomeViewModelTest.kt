package com.github.crisacm.todotesting.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.useCases.AddNewTaskUseCase
import com.github.crisacm.core.domain.useCases.DeleteTaskUseCase
import com.github.crisacm.core.domain.useCases.EditTaskUseCase
import com.github.crisacm.core.domain.useCases.GetAllTaskUseCase
import com.github.crisacm.todotesting.getOrAwaitValueTest
import com.github.crisacm.todotesting.rules.MainDispatcherRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var getAllTasksUseCase: GetAllTaskUseCase
    private lateinit var addNewTaskUseCase: AddNewTaskUseCase
    private lateinit var editTaskUseCase: EditTaskUseCase
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    private val tasksObserver: Observer<List<Task>> = mock()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        getAllTasksUseCase = mock()
        addNewTaskUseCase = mock()
        editTaskUseCase = mock()
        deleteTaskUseCase = mock()

        homeViewModel = HomeViewModel(
            getAllTasksUseCase,
            addNewTaskUseCase,
            editTaskUseCase,
            deleteTaskUseCase
        )
    }

    @After
    fun tearDown() {
        homeViewModel.tasks.removeObserver(tasksObserver)
    }

    @Test
    fun `getTasks should update LiveData with tasks from use case`() = runTest {
        // Given
        val mockTasks = listOf(
            Task(id = 1, title = "Task 1", dueDate = null, isCompleted = false),
            Task(id = 2, title = "Task 2", dueDate = 1234567890L, isCompleted = true)
        )
        `when`(getAllTasksUseCase()).thenReturn(flowOf(mockTasks))

        // When
        homeViewModel.getTasks()
        advanceUntilIdle()

        // Then
        assertEquals(mockTasks, homeViewModel.tasks.getOrAwaitValueTest())
    }

    @Test
    fun `addTask should call addNewTaskUseCase with correct parameters`() = runTest {
        // Given
        val title = "New Task"
        val dueDate = 1234567890L
        `when`(addNewTaskUseCase(Task(null, title, dueDate, false))).thenReturn(Unit)

        // When
        homeViewModel.addTask(title, dueDate)
        advanceUntilIdle()

        // Then
        verify(addNewTaskUseCase).invoke(
            Task(
                id = null,
                title = title,
                dueDate = dueDate,
                isCompleted = false
            )
        )
    }

    @Test
    fun `updateTask should invoke editTaskUseCase with correct task`() = runTest {
        // Given
        val task = Task(1, "Task 1", null, true)
        val newTitle = "Updated task"
        `when`(editTaskUseCase(task.copy(title = newTitle))).thenReturn(Unit)

        // When
        task.title = newTitle
        homeViewModel.updateTask(task)
        advanceUntilIdle()

        // Then
        verify(editTaskUseCase).invoke(task)
    }

    @Test
    fun `deleteTask should invoke deleteTaskUseCase with correct task`() = runTest {
        // Given
        val task = Task(1, "Task to delete", null, false)
        `when`(deleteTaskUseCase(task)).thenReturn(Unit)

        // When
        homeViewModel.deleteTask(task)
        advanceUntilIdle()

        // Then
        verify(deleteTaskUseCase).invoke(task)
    }
}