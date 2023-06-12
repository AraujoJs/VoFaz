package com.example.vofaz.common.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.vofaz.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object Database {

    val userAuth = hashSetOf<UserAuth>()

    val categoriesTaskData: MutableMap<String, CategoryTask> = mutableMapOf()


    var sessionAuth: UserAuth? = null

    init {
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "Arthur Lima", "a@a.com", "123456789"))
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "José Araujo", "b@b.com", "987654321"))
        sessionAuth = userAuth.elementAt(0)

//        val tasks = mutableListOf<Task>()
//
//        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Estudar Historia", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(1), false))
//        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Passear Cachorro", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(3), false))
//        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Acabar atividades", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(5), false))
//        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Programar", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(7), false))
//
//        categoriesTaskData["today"] = CategoryTask(R.string.today_task, tasks, true)
//        categoriesTaskData["tomorrow"] = CategoryTask(R.string.tomorrow_task, tasks, false)
//        categoriesTaskData["other"] = CategoryTask(R.string.format_date_task, tasks, false)


    }

}