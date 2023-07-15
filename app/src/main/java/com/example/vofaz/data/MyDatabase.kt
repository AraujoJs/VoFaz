package com.example.vofaz.data

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vofaz.common.model.Task
import java.time.LocalDate
import java.time.LocalTime

interface MyDatabase {
    fun login(email: String, password: String, callback: MyCallback)
    fun create(name: String, email: String, password: String, callback: MyCallback)
    fun addTask(day: String, @DrawableRes icon: Int, name: String, date: LocalDate?, time: LocalTime?, callback: MyCallback)

    fun addCategory(context: Context, day: String, @StringRes name: Int, tasks: MutableList<Task>, isExpanded: Boolean, callback: MyCallback)
}