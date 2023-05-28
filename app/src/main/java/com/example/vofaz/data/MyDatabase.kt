package com.example.vofaz.data

import androidx.annotation.DrawableRes
import java.time.LocalDate
import java.time.LocalTime

interface MyDatabase {
    fun login(email: String, password: String, callback: MyCallback)
    fun create(name: String, email: String, password: String, callback: MyCallback)
    fun add(@DrawableRes icon: Int, name: String, date: LocalDate?, time: LocalTime?, callback: MyCallback)
}