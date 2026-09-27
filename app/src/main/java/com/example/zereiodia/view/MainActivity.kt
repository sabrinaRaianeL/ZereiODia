package com.example.zereiodia.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zereiodia.adapter.TaskAdapter
import com.example.zereiodia.databinding.ActivityMainBinding
import com.example.zereiodia.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TodoViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Infla o layout usando View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {

        // Inicializa o adapter passando as ações que ele deve repassar para o ViewModel
        adapter = TaskAdapter (
            onTaskChecked = {task -> viewModel.toggleTask(task.id)},
            onTaskDeleted = {task -> viewModel.deleteTask(task.id)}
        )

        // Define que a lista deve ser na vertical
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }

    private fun setupListeners() {

        // Ação de clique do botão Adicionar
        binding.btnAddTask.setOnClickListener {
            val text = binding.etTaskTitle.text?.toString().orEmpty()
            if (text.isNotBlank()) {
                viewModel.addTask(text)
                binding.etTaskTitle.text?.clear() // limpa o campo depois de digitar
            }
        }
    }

    private fun observeViewModel() {
        viewModel.tasks.observe(this) {tasks -> adapter.submitList(tasks)}
    }

    private fun setupEdgeToEdge() {

        val initialPaddingLeft = binding.main.paddingLeft
        val initialPaddingTop = binding.main.paddingTop
        val initialPaddingRight = binding.main.paddingRight
        val initialPaddingBottom = binding.main.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars())
            view.setPadding (
                initialPaddingLeft + systemBars.left,
                initialPaddingTop + systemBars.top,
                initialPaddingRight + systemBars.right,
                initialPaddingBottom + systemBars.bottom
            )
            insets
        }
    }
}