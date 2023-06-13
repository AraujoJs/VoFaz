package com.example.vofaz.common.base

import com.example.vofaz.main.add.Add
import com.example.vofaz.login.Login
import com.example.vofaz.main.Main
import com.example.vofaz.register.Register
import com.example.vofaz.data.FakeDatabase
import com.example.vofaz.data.Repository
import com.example.vofaz.main.add.presenter.AddPresenter
import com.example.vofaz.login.presenter.LoginPresenter
import com.example.vofaz.main.presenter.MainPresenter
import com.example.vofaz.register.presenter.RegisterPresenter

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
