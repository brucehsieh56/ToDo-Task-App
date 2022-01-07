package app.todotask.screen.todotaskscreen.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.todotask.screen.todotaskscreen.domain.model.TaskPriority
import app.todotask.screen.todotaskscreen.domain.model.ToDoTask
import java.text.SimpleDateFormat
import java.util.*

/**
 * A stateless card representing a [ToDoTask].
 * */
@ExperimentalComposeUiApi
@Composable
fun ToDoTaskCard(
    toDoTask: ToDoTask,
    onTaskEdit: () -> Unit,
    onTaskCompleted: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isTaskDone = toDoTask.isTaskDone
            val timeText = if (isTaskDone) {
                toDoTask.timeDone?.toDateString() ?: ""
            } else {
                toDoTask.timeCreated.toDateString()
            }

            Checkbox(
                checked = isTaskDone,
                onCheckedChange = { onTaskCompleted() },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = when (toDoTask.priority) {
                        TaskPriority.P1 -> MaterialTheme.colorScheme.error
                        TaskPriority.P2 -> Color.Cyan
                        TaskPriority.P3 -> Color.Green
                        TaskPriority.P4 -> MaterialTheme.colorScheme.onSurface
                    }
                )
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onTaskEdit()
                        keyboardController?.show()
                    },
                text = toDoTask.taskName,
                textDecoration = if (isTaskDone) TextDecoration.LineThrough else null,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = getProperTimeFormat(timeText),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * Function that returns a time string if the input [dateString] is today, otherwise a date string.
 * */
private fun getProperTimeFormat(dateString: String): String {
    return if (dateString.isToday()) {
        dateString.split(" ")[1]
    } else {
        dateString.split(" ")[0]
    }
}

/**
 * An extension function to check if the current Date [String] is today.
 * */
private fun String.isToday(): Boolean {
    val today = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
    return contains(today, ignoreCase = true)
}

/**
 * An extension function to convert [Long] to Date [String].
 * */
private fun Long.toDateString(): String {
    return SimpleDateFormat("MM/dd/yyyy hh:ss:mm", Locale.getDefault()).format(Date(this))
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewToDoTaskCard() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val toDoTask = ToDoTask(
            taskName = "Create an app",
            timeCreated = 1641040780834L
        )

        ToDoTaskCard(
            toDoTask = toDoTask,
            onTaskEdit = {},
            onTaskCompleted = {}
        )
    }
}