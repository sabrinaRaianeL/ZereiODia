package com.example.zereiodia.data

import com.example.zereiodia.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // Fluxos para ordenar as tarefas
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val allTasksByTitle: Flow<List<Task>> = taskDao.getAllTasksByTitle()
    val allTasksByDueDate: Flow<List<Task>> = taskDao.getAllTasksByDueDate()

    // Funções para a tela do dashboard (agora exigem a data para filtrar o dia atual)
    fun getTotalTasksCount(date: String): Flow<Int> {
        return taskDao.getTotalTasksCount(date)
    }

    fun getCompletedTasksCount(date: String): Flow<Int> {
        return taskDao.getCompletedTasksCount(date)
    }

    fun getPendingTasksCount(date: String): Flow<Int> {
        return taskDao.getPendingTasksCount(date)
    }

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