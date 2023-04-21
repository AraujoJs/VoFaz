package com.example.vofaz

import androidx.annotation.StringRes
import com.example.vofaz.common.base.BasePresenter
import com.example.vofaz.common.base.BaseView

interface Register {

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun displayPasswordNotEquals(@StringRes notEqualsError: Int?)
        fun onUserNotCreated(message: String)
        fun onUserCreated()
    }

    interface Presenter: BasePresenter<View> {
        fun create(name: String, email: String, password: String, confirm: String)
    }
}