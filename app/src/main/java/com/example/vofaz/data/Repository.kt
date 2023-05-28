package com.example.vofaz.data

import androidx.annotation.DrawableRes
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


    fun add(@DrawableRes icon: Int, name: String, date: LocalDate?, time: LocalTime?, callback: MyCallback) {
        database.add(icon, name, date, time, callback)
    }
}