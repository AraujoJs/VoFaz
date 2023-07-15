package com.example.vofaz.main.add.presenter

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.example.vofaz.R
import com.example.vofaz.common.model.Database
import com.example.vofaz.common.model.Task
import com.example.vofaz.data.MyCallback
import com.example.vofaz.data.Repository
import com.example.vofaz.main.add.Add
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class AddPresenter(
    override var view: Add.View?,
    private var repository: Repository? = null,
    private var context: Context
) : Add.Presenter {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun add(

        @DrawableRes icon: Int,
        name: String,
        date: LocalDate?,
        time: LocalTime?,
        isToday: Boolean
    ): Boolean {
        val isNameInvalid = name.length < 3
        val isTimeInvalid = isToday && LocalTime.now() > time
        val isDataInvalid = LocalDate.now() > date

        val categories = Database.categoriesTaskData

        if (isNameInvalid) {
            view?.displayNameError(R.string.name_error)
            return false
        } else {
            view?.displayNameError(null)
        }
        if (isTimeInvalid) {
            view?.displayTimeError(R.string.time_error)
            return false
        } else {
            view?.displayTimeError(null)
        }
        if (isDataInvalid) {
            view?.displayDateError(R.string.date_error)
            return false
        } else {
            view?.displayDateError(null)
        }

        val day = if (date?.isEqual(LocalDate.now()) == true) {
            "today"
        } else if (date?.isEqual(LocalDate.now().plusDays(1)) == true) {
            "tomorrow"
        } else {
            date.toString()
        }

        when (day) {
            "today" -> {
                if (categories.containsKey("today")) {
                    addTask(day, icon, name, date, time)
                } else {
                    addCategory(day, R.string.today_task, mutableListOf(), context)
                    addTask(day, icon, name, date, time)
                }
            }
            "tomorrow" -> {
                if (categories.containsKey("tomorrow")) {
                    addTask(day, icon, name, date, time)
                } else {
                    addCategory(day, R.string.tomorrow_task, mutableListOf(), context)
                    addTask(day, icon, name, date, time)
                }
            }
            else -> {
                if (categories.containsKey(date.toString())) {
                    addTask(day, icon, name, date, time)
                } else {
                    addCategory(day, R.string.format_date_task, mutableListOf(), context)
                    addTask(day, icon, name, date, time)
                }
            }
        }
        return true
    }


@RequiresApi(Build.VERSION_CODES.O)
override fun incrementToHour(time: LocalTime, isToday: Boolean): LocalTime {
    val currentTime = LocalTime.now().plusMinutes(5)
    return when {
        isToday -> {
            if (time.plusHours(1).hour >= currentTime.hour) {
                time.plusHours(1)
            } else {
                currentTime
            }
        }
        else -> time.plusHours(1)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
override fun decrementToHour(time: LocalTime, isToday: Boolean): LocalTime {
    val currentTime = LocalTime.now().plusMinutes(5)
    return when {
        isToday -> {
            if (time.minusHours(1).hour >= currentTime.hour) {
                time.minusHours(1)
            } else {
                time.withHour(23)
            }
        }
        else -> time.minusHours(1)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
override fun incrementToMinute(time: LocalTime, isToday: Boolean): LocalTime {
    val currentTime = LocalTime.now().plusMinutes(5)
    return when {
        isToday && time.hour == currentTime.hour -> {
            if (time.plusMinutes(1).minute >= currentTime.minute) {
                time.plusMinutes(1)
            } else {
                currentTime
            }
        }
        else -> time.plusMinutes(1)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
override fun decrementToMinute(time: LocalTime, isToday: Boolean): LocalTime {
    val currentTime = LocalTime.now().plusMinutes(5)
    return when {
        isToday && time.hour == currentTime.hour -> {
            if (time.minusMinutes(1).minute >= currentTime.minute) {
                time.minusMinutes(1)
            } else {
                time.withMinute(59)
            }
        }
        else -> time.minusMinutes(1)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
override fun getDatePickerDialog(context: Context): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
        view?.setDate(LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth))
        view?.setBtnDate()
    }, year, month, day)
}

@RequiresApi(Build.VERSION_CODES.O)
override fun getTimePickerDialog(context: Context): TimePickerDialog {
    val now = LocalTime.now()

    val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        view?.setTime(LocalTime.of(hourOfDay, minute))

    }


    return TimePickerDialog(
        context,
        listener,
        now.hour,
        now.minute,
        true
    )
}

override fun onDestroy() {
    view = null
    repository = null
}
    private fun addTask(day: String, icon: Int, name: String, date: LocalDate?, time: LocalTime?) {
        repository?.addTask(day, icon, name, date, time, object : MyCallback {
            override fun onSuccess() {
                Log.i(
                    "Task:",
                    "Name: ${name}, Date:${date}, time: ${time.toString()} Icon ref:${icon}"
                )
                view?.goToMainScreen()
            }

            override fun onFailure(message: String) {
                view?.displayOnFailure(message)
            }

            override fun onComplete() {

            }
        })
    }

    private fun addCategory(day: String, name: Int, tasks: MutableList<Task>, context: Context) {
        repository?.addCategory(context, day, name, tasks, false,  object : MyCallback {
            override fun onSuccess() {

            }
            override fun onFailure(message: String) {
                view?.displayOnFailure("Can't implement category")
            }
            override fun onComplete() {
            }
        })
    }
}
