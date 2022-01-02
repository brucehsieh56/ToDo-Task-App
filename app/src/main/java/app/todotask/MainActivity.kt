package app.todotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.todotask.model.TaskPriority
import app.todotask.model.ToDoTask
import app.todotask.screen.todotaskscreen.components.ToDoTaskCard
import app.todotask.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                var toDoTask by remember {
                    mutableStateOf(
                        ToDoTask(
                            taskName = "Sleep for 12 hours tonight.",
                            priority = TaskPriority.DEFAULT
                        )
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    ToDoTaskCard(toDoTask = toDoTask) {
                        toDoTask = toDoTask.copy(
                            timeDone = if (toDoTask.timeDone == null) {
                                System.currentTimeMillis()
                            } else {
                                null
                            }
                        )
                    }
                }
            }
        }
    }
}