package com.example.vofaz.common.base

interface BasePresenter<T> {
    val view: T?
    fun onDestroy()
}