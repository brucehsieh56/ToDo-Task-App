package app.todotask.screen.todotaskscreen.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.todotask.common.DummyData
import app.todotask.screen.todotaskscreen.domain.model.ToDoTask

class ToDoTaskScreenViewModel : ViewModel() {

    var toDoTasks = mutableStateListOf<ToDoTask>()
        private set

    private var currentSelected by mutableStateOf(-1)
    val currentEditToDoTask: ToDoTask?
        get() = toDoTasks.getOrNull(currentSelected)

    init {
        DummyData.toDoTasks.forEach(::onItemAdded)
    }

    /**
     * Prepend [newItem].
     * */
    fun onItemAdded(newItem: ToDoTask) {
        toDoTasks.add(0, newItem)
        resetItemSelected()
    }

    /**
     * Update the [currentSelected].
     * */
    fun onItemSelected(item: ToDoTask) {
        currentSelected = toDoTasks.indexOf(item)
    }

    /**
     * Update [currentEditToDoTask].
     * */
    fun onItemUpdated(updatedItem: ToDoTask) {
        val currentToDoTask = requireNotNull(currentEditToDoTask)
        require(currentToDoTask.uuid == updatedItem.uuid) {}
        toDoTasks[currentSelected] = updatedItem

        resetItemSelected()
    }

    /**
     * Set or reset [ToDoTask] timeDone as a result of clicking the check box.
     * */
    fun onItemCompleted(item: ToDoTask) {

        val index = toDoTasks.indexOf(item)

        toDoTasks[index] = item.copy(
            timeDone = if (item.timeDone == null) {
                // ToDoTask is done, set timestamp
                System.currentTimeMillis()
            } else {
                // Reset ToDoTask to not done, reset timestamp
                null
            }
        )

        resetItemSelected()
    }

    /**
     * Reset the [currentSelected] to -1, which means no selection.
     * */
    private fun resetItemSelected() {
        currentSelected = -1
    }
}