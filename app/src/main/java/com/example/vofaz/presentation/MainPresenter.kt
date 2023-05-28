package com.example.vofaz.presentation

import com.example.vofaz.Main
import com.example.vofaz.common.model.Database
import com.example.vofaz.data.Repository

class MainPresenter(
    override var view: Main.View?,
    private var repository: Repository? = null
    ) : Main.Presenter {


    override fun getFirstName(fullName: String?): String {
        return fullName?.let { it.split(" ")[0] } ?: "None"
    }

    override fun getTasks(isTodoSelected: Boolean) {


        view?.showTasks(isTodoSelected)
    }

    override fun onDestroy() {
        view = null
    }

}