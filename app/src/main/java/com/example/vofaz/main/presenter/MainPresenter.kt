package com.example.vofaz.main.presenter

import com.example.vofaz.data.Repository
import com.example.vofaz.main.Main

class MainPresenter(
    override var view: Main.View?,
    private var repository: Repository? = null
    ) : Main.Presenter {


    override fun getFirstName(fullName: String?): String {
        return fullName?.let { it.split(" ")[0] } ?: "None"
    }

    override fun getTasks(isTodoSelected: Boolean) {
//        view?.showTasks(isTodoSelected, task)
    }

    override fun onDestroy() {
        view = null
    }

}