package com.example.vofaz.main

import com.example.vofaz.common.base.BasePresenter
import com.example.vofaz.common.base.BaseView
import com.example.vofaz.common.model.Task

interface Main {
    interface View: BaseView<Presenter> {
        fun showTasks(isTodoSelected: Boolean, task: Task)
        fun notifyData()

    }

    interface Presenter: BasePresenter<View> {
        fun getFirstName(fullName: String?): String
        fun getTasks(isTodoSelected: Boolean)
    }
}