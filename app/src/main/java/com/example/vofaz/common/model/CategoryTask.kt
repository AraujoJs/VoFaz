package com.example.vofaz.common.model

import android.util.ArraySet
import androidx.annotation.StringRes

data class CategoryTask(
    @StringRes val name: Int,
    val tasks: MutableList<Task>? = null,
    var isExpanded: Boolean = false
)
