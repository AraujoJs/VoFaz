package com.example.vofaz.data

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vofaz.common.model.Task
import java.time.LocalDate
import java.time.LocalTime

class Repository(
    private val database: MyDatabase
    ) {
    fun login(email: String, password: String, callback: MyCallback) {
        database.login(email, password, callback)
    }

    fun create(name: String, email: String, password: String, callback: MyCallback) {
        database.create(name, email, password, callback)
    }


    fun addTask(day: String, @DrawableRes icon: Int, name: String, date: LocalDate?, time: LocalTime?, callback: MyCallback) {
        database.addTask(day, icon, name, date, time, callback)
    }

    fun addCategory(context: Context, day: String, @StringRes name: Int, tasks: MutableList<Task>, isExpanded: Boolean, callback: MyCallback) {
        database.addCategory(context, day, name, tasks, isExpanded, callback)
    }
}