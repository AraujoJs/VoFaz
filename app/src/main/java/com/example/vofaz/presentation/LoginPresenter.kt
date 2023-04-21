package com.example.vofaz.presentation

import android.os.Handler
import android.os.Looper
import android.util.Patterns
import com.example.vofaz.Login
import com.example.vofaz.R
import com.example.vofaz.data.MyCallback
import com.example.vofaz.data.Repository


class LoginPresenter(
    override var view: Login.View?,
    private val repository: Repository
    ): Login.Presenter {

    override fun login(email: String, password: String) {
        view?.showProgress(true)

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8


        Handler(Looper.getMainLooper()).postDelayed({
            if (!isEmailValid) {
                view?.displayEmailFailure(R.string.email_error)
            } else {
                view?.displayEmailFailure(null)
            }
            if (!isPasswordValid) {
                view?.displayPasswordFailure(R.string.password_error)
            }
            else {
                view?.displayPasswordFailure(null)
            }
            view?.showProgress(false)

        }, 2000)


        if (isEmailValid && isPasswordValid) {
            repository.login(email, password, object : MyCallback {
                override fun onSuccess() {
                    view?.onUserAuthenticated()
                }

                override fun onFailure(message: String) {
                    view?.onUserUnauthenticated(message)
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