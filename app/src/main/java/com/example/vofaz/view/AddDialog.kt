package com.example.vofaz.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.vofaz.Add
import com.example.vofaz.Main
import com.example.vofaz.R
import com.example.vofaz.common.base.DependencyInjector
import com.example.vofaz.databinding.DialogAddBinding
import com.example.vofaz.databinding.HourLayoutBinding
import com.example.vofaz.databinding.MinuteLayoutBinding
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class AddDialog(val view: Main.View) : DialogFragment(), Add.View {
    override lateinit var presenter: Add.Presenter
    private lateinit var binding: DialogAddBinding

    private var name: String = "none"

    private lateinit var date: LocalDate
    private lateinit var time: LocalTime

    private var isToday = false
    private var isTomorrow = false



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = layoutInflater.inflate(R.layout.dialog_add, null)

        binding = DialogAddBinding.bind(view)
        presenter = DependencyInjector.addPresenter(this)
        date = LocalDate.now()
        time = LocalTime.now().plusMinutes(5)

        val builder = AlertDialog.Builder(activity)
            .setView(view)
            .setPositiveButton(R.string.save) { _, _ ->
                name = binding.addEditName.text.toString()
                presenter.add(R.drawable.icon_book, name, date, time, isToday)
            }
            .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.setOnShowListener {
            val positiveButton =
                alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(view.context, R.color.orange_200))

            val negativeButton =
                alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(view.context, R.color.gray_200))

            with(binding) {
                btnExit.setOnClickListener {
                    alertDialog.dismiss()
                }

                val hourLayout = timePickerLayout.hourLayout
                val minuteLayout = timePickerLayout.minutesLayout

                val btnHourUp = hourLayout.hourBtnUp
                val btnHourDown = hourLayout.hourBtnDown
                val btnMinUp = minuteLayout.minBtnUp
                val btnMinDown = minuteLayout.minBtnDown

                val datePickerDialog = presenter.getDatePickerDialog(view.context)
                val timePickerDialog = presenter.getTimePickerDialog(view.context)




                startTimeConfig(hourLayout, minuteLayout)

                addBtnToday.setOnClickListener {
                    isToday = true
                    isTomorrow = false

                    date = LocalDate.now()
                    val now = LocalTime.now()

                    if (time < now) {
                        time = now
                        setTime(time)
                    }

                    setButtonColors(
                        addBtnToday, R.color.orange_200, addBtnTomorrow,
                        R.color.gray_100, addBtnDate, R.color.gray_100, R.string.select_date
                    )
                }
                addBtnToday.performClick()

                addBtnTomorrow.setOnClickListener {
                    isTomorrow = true
                    isToday = false
                    date = LocalDate.now().plusDays(1)

                    setButtonColors(
                        addBtnToday, R.color.gray_100, addBtnTomorrow,
                        R.color.orange_200, addBtnDate, R.color.gray_100, R.string.select_date
                    )
                }

                addBtnDate.setOnClickListener {
                    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
                    datePickerDialog.show()
                    isTomorrow = false
                    isToday = false

                    addBtnToday.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray_100
                        )
                    )
                    addBtnTomorrow.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray_100
                        )
                    )

                    setButtonColors(
                        addBtnToday, R.color.gray_100, addBtnTomorrow,
                        R.color.gray_100, addBtnDate, R.color.orange_200, null
                    )
                }


                btnHourUp.setOnClickListener {
                    time = presenter.incrementToHour(time, isToday)
                    formatHourString(timePickerLayout.hourLayout.hourTxtHour)
                }

                btnHourDown.setOnClickListener {
                    time = presenter.decrementToHour(time, isToday)
                    formatHourString(timePickerLayout.hourLayout.hourTxtHour)
                }

                btnMinUp.setOnClickListener {
                    time = presenter.incrementToMinute(time, isToday)
                    formatMinuteString(timePickerLayout.minutesLayout.minTxtMin)
                }

                btnMinDown.setOnClickListener {
                    time = presenter.decrementToMinute(time, isToday)
                    formatMinuteString(timePickerLayout.minutesLayout.minTxtMin)
                }

                hourLayout.hourTxtHour.setOnClickListener {
                    val time = timePickerDialog.show()
                    Log.i("Time", time.toString())
                }
                minuteLayout.minTxtMin.setOnClickListener {
                    timePickerDialog.show()
                }
            }

        }
        return alertDialog
    }

    override fun displayNameError(nameError: Int?) {
        binding.addEditName.error = nameError?.let { getString(it) }
    }

    override fun displayTimeError(timeError: Int?) {
        timeError?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    override fun displayDateError(dateError: Int?) {
        dateError?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }

    }

    override fun displayOnFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun setButtonColors(
        btnToday: Button, todayColor: Int,
        btnTomorrow: Button, tomorrowColor: Int,
        btnDate: Button, dateColor: Int,
        @StringRes string: Int?
    ) {

        btnToday.setTextColor(ContextCompat.getColor(requireContext(), todayColor))
        btnTomorrow.setTextColor(ContextCompat.getColor(requireContext(), tomorrowColor))
        btnDate.setTextColor(ContextCompat.getColor(requireContext(), dateColor))
        btnDate.text = string?.let { getString(it) }

    }


    override fun goToMainScreen() {
        view.notifyData()

        dialog?.dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add, container, false)
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.main_background
            )
        )
        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatHourString(layout: TextView) {
        if (time.hour < 10) {
            layout.text = getString(R.string.hour_with_0, time.hour)
        } else {
            layout.text = getString(R.string.hour, time.hour)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatMinuteString(layout: TextView) {
        if (time.minute < 10) {
            layout.text = getString(R.string.min_with_0, time.minute)
        } else {
            layout.text = time.minute.toString()
        }
    }

    override fun setDate(sDate: LocalDate) {
        date = sDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setTime(sTime: LocalTime) {
        val now = LocalTime.now()

        if (isToday) {
            if (sTime.hour > now.hour || sTime.hour == now.hour && sTime.minute > now.minute) {
                time = sTime
            }
        } else {
            time = sTime
        }

        formatHourString(binding.timePickerLayout.hourLayout.hourTxtHour)
        formatMinuteString(binding.timePickerLayout.minutesLayout.minTxtMin)
        
    }

    override fun setBtnDate() {
        binding.addBtnDate.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.orange_200
            )
        )
        binding.addBtnDate.text = date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startTimeConfig(hourLayout: HourLayoutBinding, minuteLayout: MinuteLayoutBinding) {
        if (time.minute > 10) {
            minuteLayout.minTxtMin.text = time.minute.toString()
        } else {
            minuteLayout.minTxtMin.text = getString(R.string.min_with_0, time.minute)
        }

        if (time.hour < 10) {
            hourLayout.hourTxtHour.text = getString(R.string.hour_with_0, time.hour)
        } else {
            hourLayout.hourTxtHour.text = getString(R.string.hour, time.hour)
        }
    }
}