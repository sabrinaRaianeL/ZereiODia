package com.example.zereiodia.model

data class Task(

    val id: Long,
    val title: String,
    val isDone: Boolean = false,
    val dueDate: String? = null
)
