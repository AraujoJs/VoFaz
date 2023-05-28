package com.example.vofaz.common.model

import android.util.ArraySet
import androidx.annotation.StringRes

data class CategoryTask(
    @StringRes val name: Int,
    val tasks: ArraySet<Task>? = null,
    var isExpanded: Boolean = false
)
