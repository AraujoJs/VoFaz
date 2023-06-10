package com.example.vofaz.data

import android.os.Build
import android.os.Looper
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.example.vofaz.common.model.Database
import com.example.vofaz.common.model.Task
import com.example.vofaz.common.model.UserAuth
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class FakeDatabase : MyDatabase {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun login(email: String, password: String, callback: MyCallback) {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.userAuth.firstOrNull { email == it.email }

            if (userAuth == null) {
                callback.onFailure("User not found.")
            } else if (password != userAuth.password) {
                callback.onFailure("Wrong password.")
            } else {
                Database.sessionAuth = userAuth
                callback.onSuccess()
            }
            callback.onComplete()
        }, 1000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun create(name: String, email: String, password: String, callback: MyCallback) {

        val user = UserAuth(UUID.randomUUID().toString(), name, email, password)

        val userAuth = Database.userAuth.firstOrNull { user == it }

        if (userAuth == null) {
            val success = Database.userAuth.add(user)
            if (success) {
                Database.sessionAuth = user
                callback.onSuccess()
            }
        }
        callback.onComplete()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun add(
        @DrawableRes icon: Int,
        name: String,
        date: LocalDate?,
        time: LocalTime?,
        callback: MyCallback
    ) {

        val task = Task(UUID.randomUUID().toString(), icon, name, date, time)
        val isAdded = Database.categoryTaskData.elementAt(0).tasks?.add(task) ?: false


        if (isAdded) callback.onSuccess() else callback.onFailure("Internal error")

    }
}