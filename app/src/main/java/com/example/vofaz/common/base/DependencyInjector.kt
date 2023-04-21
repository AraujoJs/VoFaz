package com.example.vofaz.common.base

import com.example.vofaz.Login
import com.example.vofaz.Register
import com.example.vofaz.data.FakeDatabase
import com.example.vofaz.data.Repository
import com.example.vofaz.presentation.LoginPresenter
import com.example.vofaz.presentation.RegisterPresenter

object DependencyInjector {


    fun loginPresenter(context: Login.View): LoginPresenter {
        return LoginPresenter(context, repository())
    }

    fun registerPresenter(context: Register.View): RegisterPresenter {
        return RegisterPresenter(context, repository())
    }

    private fun repository(): Repository {
        return Repository(FakeDatabase())
    }
}
