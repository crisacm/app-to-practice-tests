package com.github.crisacm.core.domain.useCases

import com.github.crisacm.core.data.repository.TaskRepositoryImpl
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.core.domain.repository.TaskRepository
import com.github.crisacm.core.domain.rules.RoomTestRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetAllTaskUseCaseTest {
    @get:Rule
    val roomTestRule = RoomTestRule()

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setup() {
        taskRepository = TaskRepositoryImpl(roomTestRule.taskDao)
    }

    @Test
    fun `should get a flow with all tasks`() = runTest {
        // Given: Prepare necessary data
        val task = Task(1, "Task 1", null, false)
        taskRepository.addTask(task)

        // When: Execute the use case
        val result = GetAllTaskUseCase(taskRepository).invoke()
        val tasks = result.first()

        // Then: Verify the result
        assertEquals(1, tasks.size)
    }
}