package com.example.zereiodia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zereiodia.model.Task

class TodoViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>(emptyList())

    val tasks: LiveData<List<Task>> = _tasks

    fun addTask(title: String) {
        if (title.isBlank()) return

        val currentList = _tasks.value.orEmpty()
        val nextId = (currentList.maxOfOrNull {it.id} ?: 0L) + 1L
        val newTask = Task (id = nextId, title = title.trim())

        _tasks.value = currentList + newTask
    }

    fun toggleTask(taskId: Long) {
        val currentList = _tasks.value.orEmpty()
        _tasks.value = currentList.map {task ->
            if (task.id == taskId) task.copy(isDone = !task.isDone) else task
        }
    }

    fun deleteTask(taskId: Long) {
        val currentList = _tasks.value.orEmpty()
        _tasks.value = currentList.filter {it.id != taskId}
    }
}