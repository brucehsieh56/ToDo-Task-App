package app.todotask.screen.todotaskscreen.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntSize

@ExperimentalComposeUiApi
@Composable
fun ToDoTaskTextField(
    inputText: String,
    onTextChange: (String) -> Unit,
    onEditorDone: () -> Unit,
    onTextFieldSizeUpdated: (IntSize) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current

    TextField(
        value = inputText,
        onValueChange = onTextChange,
        modifier = Modifier.onGloballyPositioned {
            onTextFieldSizeUpdated(it.size)
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onEditorDone()
                localFocusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        placeholder = {
            Text(
                text = "Write a new task",
                color = MaterialTheme.colorScheme.outline
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Text input")
        }
    )
}