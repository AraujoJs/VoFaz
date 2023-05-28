package com.example.vofaz.common.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.collection.arraySetOf
import com.example.vofaz.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
object Database {

    val userAuth = hashSetOf<UserAuth>()

    val tasks = arraySetOf<Task>()


    var sessionAuth: UserAuth? = null

    init {
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "Arthur Lima", "a@a.com", "123456789"))
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "José Araujo", "b@b.com", "987654321"))
        sessionAuth = userAuth.elementAt(0)

        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Estudar Historia", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(1)))
        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Passear Cachorro", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(3)))
        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Acabar atividades", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(5)))
        tasks.add(Task(UUID.randomUUID().toString(), R.drawable.icon_book, "Programar", LocalDate.of(2023, 5, 8), LocalTime.now().plusHours(7)))



    }

}