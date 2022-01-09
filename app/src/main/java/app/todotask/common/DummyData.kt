package app.todotask.common

import app.todotask.common.data.local.TaskPriority
import app.todotask.common.data.local.ToDoTask

object DummyData {
    val toDoTasks = listOf(
        ToDoTask(
            taskName = "Sleep for 12 hours tonight",
            priority = TaskPriority.DEFAULT
        ),
        ToDoTask(
            taskName = "Buy Coke",
            priority = TaskPriority.P1
        ),
        ToDoTask(
            taskName = "Feed cat",
            priority = TaskPriority.P1
        ),
        ToDoTask(
            taskName = "Create a new Android project",
            priority = TaskPriority.P3
        ),
        ToDoTask(
            taskName = "Integrate Room database",
            priority = TaskPriority.P2
        ),
        ToDoTask(
            taskName = "Clean living room",
            priority = TaskPriority.P3
        ),
        ToDoTask(
            taskName = "Wash dishes",
            priority = TaskPriority.P4
        ),
        ToDoTask(
            taskName = "Call Mom",
            priority = TaskPriority.P3
        ),
        ToDoTask(
            taskName = "Fix bugs",
            priority = TaskPriority.P4
        ),
        ToDoTask(
            taskName = "Drink coffee",
            priority = TaskPriority.P4
        ),
        ToDoTask(
            taskName = "Submit the App to Google Store",
            priority = TaskPriority.P1
        ),
        ToDoTask(
            taskName = "Reply emails",
            priority = TaskPriority.P1
        )
    )
}