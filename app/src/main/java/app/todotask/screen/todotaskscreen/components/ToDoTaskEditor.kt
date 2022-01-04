package app.todotask.screen.todotaskscreen.components

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
import app.todotask.model.TaskPriority
import app.todotask.model.ToDoTask

/**
 * A Composable for users to write a [ToDoTask].
 * */
@ExperimentalComposeUiApi
@Composable
fun ToDoTaskEditor(
    modifier: Modifier = Modifier,
    onSubmit: (ToDoTask) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Column(modifier = Modifier.animateContentSize()) {

            val (inputText, setInputText) = remember { mutableStateOf("") }
            var textFieldSize by remember { mutableStateOf(IntSize(1, 1)) }
            var selectedPriority by remember { mutableStateOf(TaskPriority.DEFAULT) }

            val onEditorDone = {
                if (inputText.isNotBlank()) {
                    val toDoTask = ToDoTask(
                        taskName = inputText,
                        priority = selectedPriority,
                    )
                    onSubmit(toDoTask)
                    setInputText(EMPTY_STRING)
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
                    selectedPriority = selectedPriority,
                    rowWidth = with(LocalDensity.current) {
                        textFieldSize.width.toDp()
                    },
                    onPrioritySelected = {
                        selectedPriority = it
                    }
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewToDoTaskEditor() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ToDoTaskEditor()
    }
}