package com.example.vofaz.main.view

import android.content.Context
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.vofaz.R
import com.example.vofaz.common.base.DependencyInjector
import com.example.vofaz.common.model.Database
import com.example.vofaz.common.model.Task
import com.example.vofaz.databinding.ActivityMainBinding
import com.example.vofaz.login.view.LoginActivity
import com.example.vofaz.main.Main
import com.example.vofaz.main.add.view.AddDialog

class MainActivity : AppCompatActivity(), Main.View, FragmentAttachListener {

    override lateinit var presenter: Main.Presenter
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: ContentFragment

    private var toolbarIsExpanded: Boolean = false
    private var isTodoSelected: Boolean = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DependencyInjector.mainPresenter(this)

        val toolbar: Toolbar = binding.mainToolbar.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        fragment = ContentFragment()
        replaceFragment(fragment)

        setButtons(true)
        presenter.getTasks(isTodoSelected)

        binding.mainToolbar.mainToolbarName.text = presenter.getFirstName(
            Database.sessionAuth?.fullName
        )
        with(binding) {
            mainBtnTodo.btnTodo.setOnClickListener {
                mainBtnTodo.btnTodo.isEnabled = false
                mainBtnCompleted.btnCompleted.isEnabled = true

                presenter.getTasks(isTodoSelected = true)
                setButtons(true)
            }

            mainBtnCompleted.btnCompleted.setOnClickListener {
                mainBtnTodo.btnTodo.isEnabled = true
                mainBtnCompleted.btnCompleted.isEnabled = false

                presenter.getTasks(isTodoSelected = false)
                setButtons(false)
            }

            mainBtnAdd.setOnClickListener {
                val addDialog = AddDialog(this@MainActivity)
                addDialog.show(supportFragmentManager, "CustomDialog")
            }
        }
    }

    private fun setButtons(isTodoSelected: Boolean) {
        with(binding) {
            val todo = mainBtnTodo.btnTodo
            val completed = mainBtnCompleted.btnCompleted

            if (isTodoSelected) {
                todo.setBackgroundColor(getBtnColor(todo.context, R.color.gray_700))
                todo.setTextColor(getBtnColor(todo.context, R.color.orange_200))

                completed.setBackgroundColor(Color.TRANSPARENT)
                completed.setTextColor(getBtnColor(completed.context, R.color.gray_200))
            } else {
                todo.setBackgroundColor(Color.TRANSPARENT)
                todo.setTextColor(getBtnColor(todo.context, R.color.gray_200))

                completed.setBackgroundColor(getBtnColor(completed.context, R.color.gray_700))
                completed.setTextColor(getBtnColor(completed.context, R.color.gray_100))
            }
        }
    }
    private fun getBtnColor(context: Context, colorInt: Int): Int{
        return ActivityCompat.getColor(context, colorInt)
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun goToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        Database.sessionAuth = null
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    override fun isTodoSelected(): Boolean {
        return isTodoSelected
    }
    override fun notifyData() {
        fragment.getData()
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
}