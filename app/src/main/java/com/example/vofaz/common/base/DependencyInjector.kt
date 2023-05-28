package com.example.vofaz.common.base

import com.example.vofaz.Add
import com.example.vofaz.Login
import com.example.vofaz.Main
import com.example.vofaz.Register
import com.example.vofaz.data.FakeDatabase
import com.example.vofaz.data.Repository
import com.example.vofaz.presentation.AddPresenter
import com.example.vofaz.presentation.LoginPresenter
import com.example.vofaz.presentation.MainPresenter
import com.example.vofaz.presentation.RegisterPresenter

object DependencyInjector {

    fun loginPresenter(context: Login.View): LoginPresenter {
        return LoginPresenter(context, repository())
    }

    fun registerPresenter(context: Register.View): RegisterPresenter {
        return RegisterPresenter(context, repository())
    }

    fun mainPresenter(context: Main.View): MainPresenter {
        return MainPresenter(context, repository())
    }

    fun addPresenter(context: Add.View): AddPresenter {
        return AddPresenter(context, repository())
    }

    private fun repository(): Repository {
        return Repository(FakeDatabase())
    }
}
