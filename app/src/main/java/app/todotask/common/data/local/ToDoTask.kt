package app.todotask.common.data.local

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that models a To Do task.
 * */
@Entity(tableName = "to_do_task_table")
data class ToDoTask(
    val taskName: String,
    val priority: TaskPriority = TaskPriority.DEFAULT,
    val createdAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null,
    @PrimaryKey val id: Int? = null
) {
    val isTaskDone get() = finishedAt != null
}

/**
 * Task priority indicates the importance of a task.
 * */
enum class TaskPriority {
    P1, P2, P3, P4;

    companion object {
        val DEFAULT = P4
        val colors = listOf(Color.Red, Color.DarkGray, Color.Gray, Color.LightGray)
    }
}
