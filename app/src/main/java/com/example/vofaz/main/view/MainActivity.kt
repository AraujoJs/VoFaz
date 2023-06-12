package com.example.vofaz.main.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.vofaz.R
import com.example.vofaz.common.base.DependencyInjector
import com.example.vofaz.common.model.Database
import com.example.vofaz.common.model.Task
import com.example.vofaz.common.util.FragmentAttachListener
import com.example.vofaz.databinding.ActivityMainBinding
import com.example.vofaz.login.view.LoginActivity
import com.example.vofaz.main.Main
import com.example.vofaz.add.view.AddDialog

class MainActivity : AppCompatActivity(), Main.View, FragmentAttachListener {

    override lateinit var presenter: Main.Presenter
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: ContentFragment

    private var toolbarIsExpanded: Boolean = false
    private var isTodoSelected: Boolean = true

    override fun isTodoSelected(): Boolean {
        return isTodoSelected
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DependencyInjector.mainPresenter(this)


        val toolbar: Toolbar = binding.mainToolbar.mainToolbar

        fragment = ContentFragment()
        replaceFragment(fragment)
        presenter.getTasks(isTodoSelected)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        binding.mainToolbar.mainToolbarName.text =
            presenter.getFirstName(Database.sessionAuth?.fullName)

        with(binding) {

            mainBtnTodo.btnTodo.setOnClickListener {
                presenter.getTasks(isTodoSelected = true)

                mainBtnTodo.btnTodo.isEnabled = false
                mainBtnCompleted.btnCompleted.isEnabled = true

            }

            mainBtnCompleted.btnCompleted.setOnClickListener {
                presenter.getTasks(isTodoSelected = false)

                mainBtnTodo.btnTodo.isEnabled = true
                mainBtnCompleted.btnCompleted.isEnabled = false
            }


            mainBtnAdd.setOnClickListener {
                val addDialog = AddDialog(this@MainActivity)
                addDialog.show(supportFragmentManager, "CustomDialog")
            }

        }


    }

    override fun notifyData() {
        fragment.notifyData()
    }

    private fun replaceFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.main_fragment) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_fragment, fragment)
                addToBackStack(null)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment, fragment)
                addToBackStack(null)

                commit()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                goToLoginScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun goToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        Database.sessionAuth = null
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    override fun expandScreen() {
        with(binding) {

            if (!toolbarIsExpanded) {
                loginEditLayoutEmail.root.visibility = View.GONE
                supportActionBar?.hide()
                toolbarIsExpanded = true
            } else {
                loginEditLayoutEmail.root.visibility = View.VISIBLE
                supportActionBar?.show()
                toolbarIsExpanded = false
            }
        }
    }



    override fun isToolbarExpanded(): Boolean = toolbarIsExpanded

    override fun showTasks(isTodoSelected: Boolean,task: Task) {
        setButtons(isTodoSelected)

    }




    private fun setButtons(isTodoSelected: Boolean) {
        with(binding) {
            if (isTodoSelected) {
                mainBtnTodo.btnTodo.setBackgroundColor(
                    ActivityCompat.getColor(mainBtnTodo.btnTodo.context, R.color.gray_700)
                )
                mainBtnTodo.btnTodo.setTextColor(
                    ActivityCompat.getColor(mainBtnTodo.btnTodo.context, R.color.orange_200)
                )
                mainBtnCompleted.btnCompleted.setBackgroundColor(
                    Color.TRANSPARENT
                )
                mainBtnCompleted.btnCompleted.setTextColor(
                    ActivityCompat.getColor(mainBtnCompleted.btnCompleted.context, R.color.gray_200)
                )

            } else {
                mainBtnTodo.btnTodo.setBackgroundColor(
                    Color.TRANSPARENT
                )
                mainBtnTodo.btnTodo.setTextColor(
                    ActivityCompat.getColor(mainBtnTodo.btnTodo.context, R.color.gray_200)
                )

                mainBtnCompleted.btnCompleted.setBackgroundColor(
                    ActivityCompat.getColor(mainBtnCompleted.btnCompleted.context, R.color.gray_700)
                )
                mainBtnCompleted.btnCompleted.setTextColor(
                    ActivityCompat.getColor(mainBtnCompleted.btnCompleted.context, R.color.gray_100)
                )
            }

        }
    }


}