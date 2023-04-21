package com.example.vofaz.data

class Repository(
    private val database: MyDatabase
    ) {
    fun login(email: String, password: String, callback: MyCallback) {
        database.login(email, password, callback)
    }

    fun create(name: String, email: String, password: String, callback: MyCallback) {
        database.create(name, email, password, callback)
    }


}