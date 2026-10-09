package com.example.zereiodia.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zereiodia.R
import com.example.zereiodia.adapter.TaskAdapter
import com.example.zereiodia.data.AppDatabase
import com.example.zereiodia.data.TaskRepository
import com.example.zereiodia.databinding.ActivityMainBinding
import com.example.zereiodia.model.Task
import com.example.zereiodia.viewmodel.TodoViewModel
import com.example.zereiodia.viewmodel.TodoViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private var selectedDueDate: String? = null

    private val viewModel: TodoViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(database.taskDao())
        TodoViewModelFactory(repository)
    }

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

        // Inicializa o adapter passando a task que ele deve repassar para o ViewModel
        adapter = TaskAdapter (
            onTaskChecked = {task -> viewModel.toggleTask(task)},
            onTaskDeleted = {task -> showDeleteConfirmationDialog(task)}
        )

        // Define que a lista deve ser na vertical
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }

    private fun setupListeners() {

        // Abre o seletor de data
        binding.tilTaskInput.setEndIconOnClickListener {
            showDatePicker()
        }

        // Ação de clique do botão Adicionar
        binding.btnAddTask.setOnClickListener {
            val text = binding.etTaskTitle.text?.toString().orEmpty()
            if (text.isNotBlank()) {
                viewModel.addTask(text, selectedDueDate)
                binding.etTaskTitle.text?.clear() // limpa o campo depois de digitar

                // Reseta a data selecionada para a próxima tarefa
                selectedDueDate = null
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect { tasks ->
                    adapter.submitList(tasks)
                }
            }
        }
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

    private fun showDeleteConfirmationDialog(task: Task) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setMessage(getString(R.string.dialog_delete_message, task.title))
            .setPositiveButton(R.string.dialog_confirm) {_, _ -> viewModel.deleteTask(task)} // só exclui se o usuário confimar
            .setNegativeButton(R.string.dialog_cancel, null) // fecha o diálogo se o usuário cancelar
            .show()
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.date_picker_title))
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            selectedDueDate = formatter.format(Date(selection))
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }
}