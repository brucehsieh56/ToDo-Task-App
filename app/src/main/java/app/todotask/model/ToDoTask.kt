package app.todotask.model

/**
 * Data class that models a To Do task.
 * */
data class ToDoTask(
    val taskName: String,
    val priority: TaskPriority = TaskPriority.DEFAULT,
    val timeCreated: Long = System.currentTimeMillis(),
    val timeDone: Long? = null
) {
    val isTaskDone get() = timeDone != null
}

/**
 * Task priority indicates the importance of a task.
 * */
enum class TaskPriority {
    P1, P2, P3, P4;

    companion object {
        val DEFAULT = P4
    }
}
