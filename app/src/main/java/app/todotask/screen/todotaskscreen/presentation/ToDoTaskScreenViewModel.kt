package app.todotask.screen.todotaskscreen.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.todotask.common.data.local.ToDoTask
import app.todotask.common.data.local.ToDoTaskDatabase
import kotlinx.coroutines.launch

class ToDoTaskScreenViewModel : ViewModel() {

    var toDoTasks by mutableStateOf<List<ToDoTask>>(emptyList())

    private var currentSelected by mutableStateOf(-1)
    val currentEditToDoTask: ToDoTask?
        get() = toDoTasks.getOrNull(currentSelected)

    lateinit var database: ToDoTaskDatabase

    fun getData() {
//        val dummyData = DummyData.toDoTasks
        viewModelScope.launch {
//            database.dao().insertToDoTasks(dummyData)
            loadItems()
        }
    }

    /**
     * Load [ToDoTask]s from database.
     * */
    private suspend fun loadItems() {
        toDoTasks = database.dao().getAllToDoTasks().reversed()
    }

    /**
     * Insert [newItem] to database.
     * */
    fun onItemAdded(newItem: ToDoTask) {
        viewModelScope.launch {
            database.dao().insertSingleToDoTask(newItem)
            loadItems()
        }
        resetItemSelected()
    }

    /**
     * Update the [currentSelected].
     * */
    fun onItemSelected(item: ToDoTask) {
        currentSelected = toDoTasks.indexOf(item)
    }

    /**
     * Update [currentEditToDoTask] in database.
     * */
    fun onItemUpdated(updatedItem: ToDoTask) {
        val currentToDoTask = requireNotNull(currentEditToDoTask)
        require(currentToDoTask.id == updatedItem.id) {}

        viewModelScope.launch {
            database.dao().updateToDoTask(updatedItem)
            loadItems()
        }

        resetItemSelected()
    }

    /**
     * Set or reset [ToDoTask] timeDone as a result of clicking the check box.
     * */
    fun onItemCompleted(item: ToDoTask) {
        val completedToDoTask = item.copy(
            finishedAt = if (item.finishedAt == null) {
                // ToDoTask is done, set timestamp
                System.currentTimeMillis()
            } else {
                // Reset ToDoTask to not done, reset timestamp
                null
            }
        )

        viewModelScope.launch {
            database.dao().updateToDoTask(completedToDoTask)
            loadItems()
        }

        resetItemSelected()
    }

    /**
     * Reset the [currentSelected] to -1, which means no selection.
     * */
    private fun resetItemSelected() {
        currentSelected = -1
    }
}