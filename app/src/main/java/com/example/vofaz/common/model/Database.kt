package com.example.vofaz.common.model

import android.os.Build
import android.util.ArraySet
import androidx.annotation.RequiresApi
import com.example.vofaz.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object Database {

    val userAuth = hashSetOf<UserAuth>()

    val categoryTaskData = mutableListOf<CategoryTask>()


    var sessionAuth: UserAuth? = null

    init {
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "Arthur Lima", "a@a.com", "123456789"))
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "José Araujo", "b@b.com", "987654321"))
        sessionAuth = userAuth.elementAt(0)

        val tasks = mutableListOf<Task>()

        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Estudar Historia", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(1)))
        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Passear Cachorro", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(3)))
        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Acabar atividades", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(5)))
        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Programar", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(7)))

        categoryTaskData.add(CategoryTask(R.string.today_task, tasks, true))
        categoryTaskData.add(CategoryTask(R.string.tomorrow_task, tasks, false))
        categoryTaskData.add(CategoryTask(R.string.format_date_task, tasks, false))


    }

}