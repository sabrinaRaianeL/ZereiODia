package com.example.zereiodia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zereiodia.data.TaskRepository
import com.example.zereiodia.data.UserPreferencesRepository

class TodoViewModelFactory (
    private val repository: TaskRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository, preferencesRepository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}