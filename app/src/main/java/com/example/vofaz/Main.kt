package com.example.vofaz

import com.example.vofaz.common.base.BasePresenter
import com.example.vofaz.common.base.BaseView

interface Main {
    interface View: BaseView<Presenter> {
        fun showTasks(isTodoSelected: Boolean)

    }

    interface Presenter: BasePresenter<View> {
        fun getFirstName(fullName: String?): String
        fun getTasks(isTodoSelected: Boolean)
    }
}