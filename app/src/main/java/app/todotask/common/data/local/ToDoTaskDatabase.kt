package app.todotask.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDoTask::class], version = 1)
abstract class ToDoTaskDatabase : RoomDatabase() {
    abstract fun dao(): ToDoTaskDao
}