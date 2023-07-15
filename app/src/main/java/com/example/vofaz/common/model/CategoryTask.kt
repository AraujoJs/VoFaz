package com.example.vofaz.common.model

data class CategoryTask(
    val name: String,
    val tasks: MutableList<Task>? = null,
    var isExpanded: Boolean = false
)
