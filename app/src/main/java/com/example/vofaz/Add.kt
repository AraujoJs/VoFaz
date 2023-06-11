package com.example.vofaz

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vofaz.common.base.BasePresenter
import com.example.vofaz.common.base.BaseView
import java.time.LocalDate
import java.time.LocalTime

interface Add {

    interface View : BaseView<Presenter> {
        fun goToMainScreen()
        fun setDate(sDate: LocalDate)
        fun setTime(sTime: LocalTime)
        fun displayNameError(@StringRes nameError: Int?)
        fun displayTimeError(@StringRes timeError: Int?)
        fun displayDateError(@StringRes dateError: Int?)
        fun displayOnFailure(message: String)
        fun setBtnDate()
    }

    interface Presenter : BasePresenter<View> {
        fun add(@DrawableRes icon: Int, name: String, date: LocalDate?, time: LocalTime?, isToday: Boolean): Boolean
        fun incrementToHour(time: LocalTime, isToday: Boolean): LocalTime
        fun decrementToHour(time: LocalTime, isToday: Boolean): LocalTime
        fun incrementToMinute(time: LocalTime, isToday: Boolean): LocalTime
        fun decrementToMinute(time: LocalTime, isToday: Boolean): LocalTime
        fun getDatePickerDialog(context: Context): DatePickerDialog
        fun getTimePickerDialog(context: Context): TimePickerDialog
    }


}