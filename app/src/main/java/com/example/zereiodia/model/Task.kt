package com.example.zereiodia.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val isDone: Boolean = false,
    val dueDate: String? = null
)
