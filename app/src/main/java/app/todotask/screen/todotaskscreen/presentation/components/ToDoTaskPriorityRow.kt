package app.todotask.screen.todotaskscreen.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import app.todotask.screen.todotaskscreen.domain.model.TaskPriority

/**
 * A Composable for users to select the [TaskPriority] for the task.
 * */
@Composable
fun ToDoTaskPriorityRow(
    selectedPriority: TaskPriority,
    rowWidth: Dp,
    onPrioritySelected: (TaskPriority) -> Unit
) {
    val selected = TaskPriority.values().indexOfFirst { it == selectedPriority }

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
                Crossfade(
                    targetState = selected == index,
                    animationSpec = tween(
                        durationMillis = 288,
                        easing = FastOutSlowInEasing
                    )
                ) { isSelected ->
                    when (isSelected) {
                        true -> Icon(
                            imageVector = Icons.Outlined.Adjust,
                            contentDescription = "Selected task priority",
                            tint = TaskPriority.colors[index]
                        )
                        false -> Icon(
                            imageVector = Icons.Outlined.Circle,
                            contentDescription = "Unselected task priority",
                            tint = TaskPriority.colors[index]
                        )
                    }
                }
            }
        }
    }
}