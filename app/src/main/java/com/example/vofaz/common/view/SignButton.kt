package com.example.vofaz.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.example.vofaz.R

class SignButton: FrameLayout {
    private lateinit var button: Button
    private lateinit var progress: ProgressBar
    private var btnText: String? = null

    constructor(context: Context):super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        setUp(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        setUp(context, attrs)
    }

    private fun setUp(context: Context, attrs: AttributeSet?) {

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_sign, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SignButton, 0, 0)

        btnText = typedArray.getString(R.styleable.SignButton_name)

        button = getChildAt(0) as Button
        progress = getChildAt(1) as ProgressBar

        button.isEnabled = false
        button.text = btnText


        typedArray.recycle()
    }

    fun showProgress(enabled: Boolean) {
        if (enabled) {
            button.text = ""
            progress.visibility = View.VISIBLE
            button.isEnabled = false
        } else {
            progress.visibility = View.GONE
            button.text = btnText
            button.isEnabled = true
        }
    }



    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

    override fun setEnabled(enabled: Boolean) {
        button.isEnabled = enabled
    }



}