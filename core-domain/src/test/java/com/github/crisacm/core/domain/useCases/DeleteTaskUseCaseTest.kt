package com.github.crisacm.core.domain.useCases

import com.github.crisacm.core.data.repository.TaskRepositoryImpl
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository
import com.github.crisacm.core.domain.rules.RoomTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DeleteTaskUseCaseTest {
    @get:Rule
    val roomTestRule = RoomTestRule()

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setUp() {
        taskRepository = TaskRepositoryImpl(roomTestRule.taskDao)
    }

    @Test
    fun `delete task`() = runTest {
        // Given
        val task = Task(
            id = 1,
            title = "Task 1",
            dueDate = 0L,
            isCompleted = false
        )
        taskRepository.addTask(task)

        // When
        DeleteTaskUseCase(taskRepository).invoke(task)

        // Then
        val tasks = taskRepository.getAll()
        assertTrue(tasks.isEmpty())
    }
}