package com.example.vofaz.login

import androidx.annotation.StringRes
import com.example.vofaz.common.base.BasePresenter
import com.example.vofaz.common.base.BaseView

interface Login {
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
         fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserUnauthenticated(message: String)
        fun onUserAuthenticated()
        fun goToRegisterScreen()

    }

    interface Presenter: BasePresenter<View> {
        fun login(email: String, password: String)
    }
}