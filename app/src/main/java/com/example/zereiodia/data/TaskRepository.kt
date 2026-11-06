package com.example.zereiodia.data

import com.example.zereiodia.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // Fluxos para ordenar as tarefas
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val allTasksByTitle: Flow<List<Task>> = taskDao.getAllTasksByTitle()
    val allTasksByDueDate: Flow<List<Task>> = taskDao.getAllTasksByDueDate()

    // Fluxos para a tela do dashboard
    val totalTasks: Flow<Int> = taskDao.getTotalTasksCount()
    val completedTasks: Flow<Int> = taskDao.getCompletedTasksCount()
    val pendingTasks: Flow<Int> = taskDao.getPendingTasksCount()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}