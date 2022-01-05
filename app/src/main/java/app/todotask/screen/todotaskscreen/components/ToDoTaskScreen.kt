package app.todotask.screen.todotaskscreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.todotask.common.DummyData
import app.todotask.model.ToDoTask
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.rememberImeNestedScrollConnection
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimatedInsets
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun ToDoTaskScreen() {

    var toDoTasks by remember { mutableStateOf(DummyData.toDoTasks) }
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsWithImePadding(),
                contentAlignment = Alignment.Center
            ) {
                ToDoTaskEditor(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    onSubmit = {
                        // Copy the toDoTasks and add a new ToDoTask to it
                        toDoTasks = toDoTasks.toMutableList().apply {
                            reverse()
                            add(it)
                        }.reversed()

                        scope.launch {
                            delay(1000)
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                )
            }
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .nestedScroll(connection = rememberImeNestedScrollConnection()),
            state = lazyListState,
            contentPadding = contentPadding,
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            itemsIndexed(
                items = toDoTasks,
                // Add key for scrolling animation
                key = { _: Int, item: ToDoTask -> item.uuid }
            ) { index, toDoTask ->

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