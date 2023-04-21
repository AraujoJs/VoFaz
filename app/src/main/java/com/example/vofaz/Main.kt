package com.example.vofaz

import com.example.vofaz.common.base.BasePresenter
import com.example.vofaz.common.base.BaseView

interface Main {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }
}