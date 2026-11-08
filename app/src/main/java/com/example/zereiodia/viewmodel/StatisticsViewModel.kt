package com.example.zereiodia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zereiodia.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class StatisticsUiState(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val completionPercentage: Float = 0f,
    val currentDate: String = "",
    val currentStreak: Int = 0
)

class StatisticsViewModel (private val repository: TaskRepository) : ViewModel() {

    private val todayFormatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val uiState: StateFlow<StatisticsUiState> = combine (
        repository.getTotalTasksCount(todayFormatted),
        repository.getCompletedTasksCount(todayFormatted),
        repository.getPendingTasksCount(todayFormatted),
        repository.getDistinctCompletionDates()
    ) { total, completed, pending, completionDates ->
        val percentage = if (total > 0) {
            completed.toFloat() / total.toFloat() * 100
        } else {
            0f
        }

        val streak = calculateStreak(completionDates)

        StatisticsUiState(
            totalTasks = total,
            completedTasks = completed,
            pendingTasks = pending,
            completionPercentage = percentage,
            currentDate = todayFormatted,
            currentStreak = streak
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticsUiState(currentDate = todayFormatted)
    )

    private fun calculateStreak(dates: List<String>): Int {
        if (dates.isEmpty()) return 0

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val dateSet = dates.toSet()

        val todayStr = sdf.format(calendar.time)

        // Verifica se alguma tarefa foi concluída na data atual ou no dia anterior
        var checkDateStr = if (dateSet.contains(todayStr)) {
            todayStr
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val yesterdayStr = sdf.format(calendar.time)
            if (dateSet.contains(yesterdayStr)) {
                yesterdayStr
            } else {
                return 0
            }
        }

        var currentStreak = 0
        while (dateSet.contains(checkDateStr)) {
            currentStreak++
            val parsedDate = sdf.parse(checkDateStr) ?: break
            calendar.time = parsedDate
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            checkDateStr = sdf.format(calendar.time)
        }

        return currentStreak
    }

}