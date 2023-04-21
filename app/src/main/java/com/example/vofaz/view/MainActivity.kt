package com.example.vofaz.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.vofaz.R
import com.example.vofaz.Register
import com.example.vofaz.common.model.Database
import com.example.vofaz.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity(), Register.View, FragmentAttachListener {

    override lateinit var presenter: Register.Presenter
    private lateinit var binding: ActivityMainBinding
    private var toolbarIsExpanded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.mainToolbar.mainToolbar


        setSupportActionBar(toolbar)

        supportActionBar?.title = ""

        with(binding) {

        }

        val fragment = ContentFragment()
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.main_content_fragment, fragment)
//            addToBackStack(null)
//            commit()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_logout -> {
                goToLoginScreen()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showProgress(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun displayNameFailure(nameError: Int?) {
        TODO("Not yet implemented")
    }

    override fun displayEmailFailure(emailError: Int?) {
        TODO("Not yet implemented")
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        TODO("Not yet implemented")
    }

    override fun displayPasswordNotEquals(notEqualsError: Int?) {
        TODO("Not yet implemented")
    }

    override fun onUserNotCreated(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUserCreated() {
        TODO("Not yet implemented")
    }

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
}