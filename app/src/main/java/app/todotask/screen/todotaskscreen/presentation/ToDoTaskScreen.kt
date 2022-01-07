package app.todotask.screen.todotaskscreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.todotask.screen.todotaskscreen.domain.model.ToDoTask
import app.todotask.screen.todotaskscreen.presentation.components.ToDoTaskCard
import app.todotask.screen.todotaskscreen.presentation.components.ToDoTaskEditor
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimatedInsets
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun ToDoTaskScreen(viewModel: ToDoTaskScreenViewModel) {

    val toDoTasks = viewModel.toDoTasks
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
                    toDoTask = viewModel.currentEditToDoTask,
                    onSubmit = {

                        if (viewModel.currentEditToDoTask == null) {
                            viewModel.onItemAdded(it)

                            // Scroll to bottom
                            scope.launch {
                                delay(1000)
                                lazyListState.animateScrollToItem(0)
                            }
                        } else {
                            viewModel.onItemUpdated(it)
                        }
                    }
                )
            }
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 4.dp),
//                .nestedScroll(connection = rememberImeNestedScrollConnection()),
            state = lazyListState,
            contentPadding = contentPadding,
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            items(
                items = toDoTasks,
                // Add key for scrolling animation
                key = { item: ToDoTask -> item.uuid }
            ) {
                ToDoTaskCard(
                    toDoTask = it,
                    onTaskCompleted = { viewModel.onItemCompleted(it) },
                    onTaskEdit = { viewModel.onItemSelected(it) }
                )
            }
        }
    }
}