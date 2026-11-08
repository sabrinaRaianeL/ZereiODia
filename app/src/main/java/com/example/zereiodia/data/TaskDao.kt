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

    // Queries para a tela do dashboard (agora refletem apenas a data atual)

    @Query("SELECT COUNT(*) FROM tasks WHERE dueDate = :date")
    fun getTotalTasksCount(date: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDone = 1 AND dueDate = :date")
    fun getCompletedTasksCount(date: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDone = 0 AND dueDate = :date")
    fun getPendingTasksCount(date: String): Flow<Int>

    // Querie para o streak

    @Query("SELECT DISTINCT completedDate FROM tasks WHERE isDone = 1 AND completedDate IS NOT NULL ORDER BY completedDate DESC")
    fun getDistinctCompletionDates(): Flow<List<String>>
}