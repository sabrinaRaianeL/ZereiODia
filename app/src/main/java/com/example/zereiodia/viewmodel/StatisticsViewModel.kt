package com.example.zereiodia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zereiodia.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class StatisticsUiState(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val completionPercentage: Float = 0f,
    val currentDate: String = ""
)

class StatisticsViewModel (private val repository: TaskRepository) : ViewModel() {

    private val todayFormatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val uiState: StateFlow<StatisticsUiState> = combine (
        repository.getTotalTasksCount(todayFormatted),
        repository.getCompletedTasksCount(todayFormatted),
        repository.getPendingTasksCount(todayFormatted)
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
            completionPercentage = percentage,
            currentDate = todayFormatted
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticsUiState(currentDate = todayFormatted)
    )

}