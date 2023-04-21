package com.example.vofaz.presentation

import android.util.Patterns
import com.example.vofaz.Register
import com.example.vofaz.R
import com.example.vofaz.data.MyCallback
import com.example.vofaz.data.Repository

class RegisterPresenter(
    override var view: Register.View? = null,
    private val repository: Repository
): Register.Presenter {

    override fun create(name: String, email: String, password: String, confirm: String) {
        val isNameValid = name.length > 3 && name.contains(" ")
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8
        val isPasswordEquals = password == confirm

        if (!isNameValid) {
            view?.displayNameFailure(R.string.name_error)
        } else {
            view?.displayNameFailure(null)
        }
        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.email_error)
        } else {
            view?.displayEmailFailure(null)
        }
        if (!isPasswordValid) {
            view?.displayPasswordFailure(R.string.password_error)
        } else {
            view?.displayPasswordFailure(null)
        }
        if (!isPasswordEquals) {
            view?.displayPasswordNotEquals(R.string.password_not_equals)
        } else {
            view?.displayPasswordNotEquals(null)
        }

        if (isNameValid && isEmailValid && isPasswordValid && isPasswordEquals) {
            view?.showProgress(true)
            repository.create(name, email, password, object : MyCallback {
                override fun onSuccess() {
                    view?.onUserCreated()
                }

                override fun onFailure(message: String) {
                    view?.onUserNotCreated(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }

            })
        }




    }

    override fun onDestroy() {
        view = null
    }
}