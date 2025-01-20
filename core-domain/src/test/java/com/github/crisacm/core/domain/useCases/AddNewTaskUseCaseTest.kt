package com.github.crisacm.core.domain.useCases

import com.github.crisacm.core.data.repository.TaskRepositoryImpl
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository
import com.github.crisacm.core.domain.rules.RoomTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddNewTaskUseCaseTest {
    @get:Rule
    val roomTestRule = RoomTestRule()

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setup() {
        taskRepository = TaskRepositoryImpl(roomTestRule.taskDao)
    }

    @Test
    fun `should add a new task`() = runTest {
        // Given: Prepare necessary data
        val task = Task(
            id = 1,
            title = "Task 1",
            dueDate = 0L,
            isCompleted = false
        )

        // When: Execute the use case
        AddNewTaskUseCase(taskRepository).invoke(task)

        // Then: Verify the result
        val tasks = taskRepository.getAll()
        assertEquals(1, tasks.size)
        assertEquals(task, tasks.first())
    }
}
