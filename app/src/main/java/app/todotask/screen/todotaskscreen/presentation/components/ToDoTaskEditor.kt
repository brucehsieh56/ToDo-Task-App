package app.todotask.screen.todotaskscreen.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import app.todotask.common.Constants.EMPTY_STRING
import app.todotask.common.data.local.TaskPriority
import app.todotask.common.data.local.ToDoTask

/**
 * A Composable for users to write a [ToDoTask].
 * */
@ExperimentalComposeUiApi
@Composable
fun ToDoTaskEditor(
    inputText: String,
    setInputText: (String) -> Unit,
    modifier: Modifier = Modifier,
    toDoTask: ToDoTask? = null,
    onSubmit: (ToDoTask) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Column(modifier = Modifier.animateContentSize()) {

//            val (inputText, setInputText) = remember { mutableStateOf(EMPTY_STRING) }
            val (taskPriority, setTaskPriority) = remember { mutableStateOf(TaskPriority.DEFAULT) }
            var textFieldSize by remember { mutableStateOf(IntSize(1, 1)) }

            val onEditorDone = {
                if (inputText.isNotBlank()) {

                    val newToDoTask = toDoTask?.copy(
                        // Update the existing task
                        taskName = inputText,
                        priority = taskPriority
                    ) ?: ToDoTask(
                        // Create a new task
                        taskName = inputText,
                        priority = taskPriority,
                    )

                    onSubmit(newToDoTask)
                    setInputText(EMPTY_STRING)
                }
            }

            LaunchedEffect(key1 = toDoTask?.taskName) {
                // Update inputText
                toDoTask?.let {
                    setInputText(it.taskName)
                    setTaskPriority(it.priority)
                }
            }

            ToDoTaskTextField(
                inputText = inputText,
                onTextChange = setInputText,
                onEditorDone = onEditorDone,
                onTextFieldSizeUpdated = {
                    textFieldSize = it
                }
            )

            // Show ToDoTaskPriorityRow when there's input text
            if (inputText.isNotBlank()) {
                ToDoTaskPriorityRow(
                    selectedPriority = taskPriority,
                    rowWidth = with(LocalDensity.current) {
                        textFieldSize.width.toDp()
                    },
                    onPrioritySelected = setTaskPriority
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewToDoTaskEditor() {

    val (inputText, setInputText) = remember { mutableStateOf(EMPTY_STRING) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ToDoTaskEditor(
            inputText = inputText,
            setInputText = setInputText,
        )
    }
}