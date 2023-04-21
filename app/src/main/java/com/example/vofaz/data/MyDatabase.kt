package com.example.vofaz.data

interface MyDatabase {
    fun login(email: String, password: String, callback: MyCallback)
    fun create(name: String, email: String, password: String, callback: MyCallback)
}