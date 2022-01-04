package app.todotask.screen.todotaskscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import app.todotask.model.TaskPriority

/**
 * A Composable for users to select the [TaskPriority] for the task.
 * */
@Composable
fun ToDoTaskPriorityRow(
    selectedPriority: TaskPriority,
    rowWidth: Dp,
    onPrioritySelected: (TaskPriority) -> Unit
) {
    val selected = TaskPriority.values().indexOfFirst {
        it == selectedPriority
    }

    Row(
        modifier = Modifier.width(rowWidth),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TaskPriority.values().forEachIndexed { index, _ ->
            IconButton(
                onClick = {
                    val taskPriority = TaskPriority.values()[index]
                    onPrioritySelected(taskPriority)
                }
            ) {
                // TODO: 1/3/22 Add animation when swapping icons
                Icon(
                    imageVector = if (selected == index) Icons.Outlined.Adjust else Icons.Outlined.Circle,
                    contentDescription = "Task priority icon",
                    tint = TaskPriority.colors[index]
                )
            }
        }
    }
}