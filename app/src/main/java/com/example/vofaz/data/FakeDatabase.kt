package com.example.vofaz.data

import android.os.Looper
import com.example.vofaz.common.model.Database
import com.example.vofaz.common.model.UserAuth
import java.util.UUID
import java.util.logging.Handler

class FakeDatabase: MyDatabase {
    override fun login(email: String, password: String, callback: MyCallback) {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.userAuth.firstOrNull{ email == it.email }

            if (userAuth == null) {
                callback.onFailure("User not found.")
            } else if (password != userAuth.password) {
                    callback.onFailure("Wrong password.")
                } else {
                    callback.onSuccess()
                }
            callback.onComplete()
        }, 1000)
    }

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

}