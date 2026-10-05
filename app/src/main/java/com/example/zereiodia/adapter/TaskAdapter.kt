package com.example.zereiodia.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zereiodia.R
import com.example.zereiodia.databinding.ItemTaskBinding
import com.example.zereiodia.model.Task

class TaskAdapter(
    private val onTaskChecked: (Task) -> Unit,
    private val onTaskDeleted: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            // Remove o listener temporariamente para evitar problemas na hora de reciclar as views
            binding.cbTaskDone.setOnCheckedChangeListener(null)

            binding.cbTaskDone.text = task.title
            binding.cbTaskDone.isChecked = task.isDone

            // Aplica/remove efeito de texto riscado nas tasks
            if (task.isDone) {
                binding.cbTaskDone.paintFlags = binding.cbTaskDone.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.cbTaskDone.paintFlags = binding.cbTaskDone.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Exibe/oculta data limite das tasks
            if (!task.dueDate.isNullOrBlank()) {
                binding.tvTaskDueDate.text = binding.root.context.getString(R.string.task_due_date, task.dueDate)
                binding.tvTaskDueDate.visibility = View.VISIBLE
            } else {
                binding.tvTaskDueDate.visibility = View.GONE
            }

            // setOnClickListener no lugar de onCheckedChange para disparar apenas com ação do usuário
            binding.cbTaskDone.setOnClickListener {
                onTaskChecked(task)
            }

            binding.btnDeleteTask.setOnClickListener {
                onTaskDeleted(task)
            }
        }
    }

    companion object TaskDiffCallBack : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}