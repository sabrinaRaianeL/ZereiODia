package com.example.zereiodia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zereiodia.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class StatisticsUiState(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val completionPercentage: Float = 0f
)

class StatisticsViewModel (private val repository: TaskRepository) : ViewModel() {

    val uiState: StateFlow<StatisticsUiState> = combine (
        repository.totalTasks,
        repository.completedTasks,
        repository.pendingTasks
    ) { total, completed, pending ->
        val percentage = if (total > 0) {
            completed.toFloat() / total.toFloat() * 100
        } else {
            0f
        }

        StatisticsUiState(
            totalTasks = total,
            completedTasks = completed,
            pendingTasks = pending,
            completionPercentage = percentage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticsUiState()
    )

}