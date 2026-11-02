package com.example.zereiodia.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.zereiodia.data.AppDatabase
import com.example.zereiodia.data.TaskRepository
import com.example.zereiodia.viewmodel.StatisticsViewModel
import com.example.zereiodia.viewmodel.StatisticsViewModelFactory

class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = TaskRepository(database.taskDao())
        StatisticsViewModelFactory(repository)
    }

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()

                StatisticsScreen(uiState = uiState)
            }
        }
    }
}