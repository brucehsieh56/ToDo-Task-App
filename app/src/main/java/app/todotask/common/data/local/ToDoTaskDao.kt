package app.todotask.common.data.local

import androidx.room.*

@Dao
abstract class ToDoTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertToDoTasks(toDoTasks: List<ToDoTask>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSingleToDoTask(toDoTask: ToDoTask)

    @Query("SELECT * FROM to_do_task_table")
    abstract suspend fun getAllToDoTasks(): List<ToDoTask>

    @Update
    abstract suspend fun updateToDoTask(toDoTask: ToDoTask)
}