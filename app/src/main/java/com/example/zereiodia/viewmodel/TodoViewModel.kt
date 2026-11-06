package com.example.zereiodia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zereiodia.data.TaskRepository
import com.example.zereiodia.data.UserPreferencesRepository
import com.example.zereiodia.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel (private val repository: TaskRepository, private val preferencesRepository: UserPreferencesRepository) : ViewModel() {

    val sortOrder = preferencesRepository.sortOrderFlow

    val tasks: StateFlow<List<Task>> = sortOrder.flatMapLatest {
        order -> when (order) {
            "TITLE" -> repository.allTasksByTitle
            "DUE_DATE" -> repository.allTasksByDueDate
            else -> repository.allTasks
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun updateSortOrder(newOrder: String) {
        viewModelScope.launch {
            preferencesRepository.saveSortOrder(newOrder)
        }
    }

    fun addTask(title: String, dueDate: String? = null) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val newTask = Task(
                title = title.trim(),
                dueDate = dueDate
            )
            repository.insert(newTask)
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isDone = !task.isDone)
            repository.update(updatedTask)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}