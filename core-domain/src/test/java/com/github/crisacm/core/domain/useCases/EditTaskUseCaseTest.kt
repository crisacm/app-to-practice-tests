package com.github.crisacm.core.domain.useCases

import com.github.crisacm.core.data.repository.TaskRepositoryImpl
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository
import com.github.crisacm.core.domain.rules.RoomTestRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EditTaskUseCaseTest {
    @get:Rule
    val roomTestRule = RoomTestRule()

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setup() {
        taskRepository = TaskRepositoryImpl(roomTestRule.taskDao)
    }

    @Test
    fun `should edit a task`() = runTest {
        // Given: Prepare necessary data
        val task = Task(
            id = 1,
            title = "Task 1",
            dueDate = 0L,
            isCompleted = false
        )
        taskRepository.addTask(task)

        // When: Execute the use case
        task.title = "Task 2"
        EditTaskUseCase(taskRepository).invoke(task)

        // Then: Verify the result
        val tasks = taskRepository.getAll()
        assertEquals(1, tasks.size)
        assertEquals(task.title, "Task 2")
    }
}