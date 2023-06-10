package com.example.vofaz.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.example.vofaz.Add
import com.example.vofaz.R
import com.example.vofaz.common.view.RvAdapter
import com.example.vofaz.data.MyCallback
import com.example.vofaz.data.Repository
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class AddPresenter(
    override var view: Add.View?,
    private var repository: Repository? = null
) : Add.Presenter {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun add(
        @DrawableRes icon: Int,
        name: String,
        date: LocalDate?,
        time: LocalTime?
    ): Boolean {
        val isNameValid = name.length > 3
        val isTimeValid = LocalTime.now() < time
        val isDateValid = LocalDate.now() <= date

        if (!isNameValid) {
            view?.displayNameError(R.string.name_error)
            return false
        } else {
            view?.displayNameError(null)
        }
        if (!isTimeValid) {
            view?.displayTimeError(R.string.time_error)
            return false
        } else {
            view?.displayTimeError(null)
        }
        if (!isDateValid) {
            view?.displayDateError(R.string.date_error)
            return false
        } else {
            view?.displayDateError(null)
        }

        repository?.add(icon, name, date, time, object : MyCallback {
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
        isToday -> {
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
}