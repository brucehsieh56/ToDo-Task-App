package app.todotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import app.todotask.model.TaskPriority
import app.todotask.model.ToDoTask
import app.todotask.screen.todotaskscreen.components.ToDoTaskCard
import app.todotask.screen.todotaskscreen.components.ToDoTaskEditor
import app.todotask.ui.theme.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.ui.Scaffold

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Remove window decor
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {

                    var toDoTasks by remember {
                        mutableStateOf(
                            listOf(
                                ToDoTask(
                                    taskName = "Sleep for 12 hours tonight",
                                    priority = TaskPriority.DEFAULT
                                ),
                                ToDoTask(
                                    taskName = "Buy Coke",
                                    priority = TaskPriority.P1
                                ),
                                ToDoTask(
                                    taskName = "Create a new Android project",
                                    priority = TaskPriority.P3
                                )
                            )
                        )
                    }

                    Scaffold(
                        bottomBar = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .navigationBarsWithImePadding(),
                                contentAlignment = Alignment.Center
                            ) {
                                ToDoTaskEditor(
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 8.dp
                                    ),
                                    onSubmit = {
                                        // Copy the toDoTasks and add a new ToDoTask to it
                                        toDoTasks = toDoTasks.toMutableList().apply { add(it) }
                                    }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { contentPadding ->
                        Column {
                            LazyColumn(
                                contentPadding = contentPadding,
//                                reverseLayout = true,
                                modifier = Modifier
                                    .weight(1f)
//                                    .nestedScroll(connection = rememberImeNestedScrollConnection())
                            ) {
                                itemsIndexed(toDoTasks) { index, toDoTask ->
                                    ToDoTaskCard(
                                        toDoTask = toDoTask,
                                        onTaskCompleted = {
                                            // Copy the toDoTasks and update the toDoTask
                                            val newToDoTasks = toDoTasks.toMutableList().apply {
                                                this[index] = toDoTask.copy(
                                                    timeDone = if (toDoTask.timeDone == null) {
                                                        System.currentTimeMillis()
                                                    } else {
                                                        null
                                                    }
                                                )
                                            }

                                            toDoTasks = newToDoTasks
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}