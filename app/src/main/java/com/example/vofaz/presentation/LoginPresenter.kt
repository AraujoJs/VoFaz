package com.example.vofaz.presentation

import android.os.Handler
import android.os.Looper
import android.util.Patterns
import com.example.vofaz.Login
import com.example.vofaz.R


class LoginPresenter(
    override var view: Login.View?
    ): Login.Presenter {

    override fun login(email: String, password: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8

        view?.showProgress(true)
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
        }, 2000)


        if (isEmailValid && isPasswordValid) {
            view?.onUserAuthenticated()
        }
        view?.showProgress(false)
    }


    override fun onDestroy() {
        view = null
    }

}