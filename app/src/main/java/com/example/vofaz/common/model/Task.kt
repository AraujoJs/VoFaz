package com.example.vofaz.common.model

import androidx.annotation.DrawableRes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Task(
    val uuid: String,
    @DrawableRes val icon: Int,
    val name: String,
    val localDate: LocalDate?,
    val localTime: LocalTime?

)
