package com.example.vofaz.data

interface MyCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}