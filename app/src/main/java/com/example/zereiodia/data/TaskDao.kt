package com.example.zereiodia.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zereiodia.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Queries para ordenação da lista de tarefas
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY title ASC")
    fun getAllTasksByTitle(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllTasksByDueDate(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    // Queries para a tela do dashboard

    @Query("SELECT COUNT(*) FROM tasks")
    fun getTotalTasksCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDone = 1")
    fun getCompletedTasksCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDone = 0")
    fun getPendingTasksCount(): Flow<Int>
}