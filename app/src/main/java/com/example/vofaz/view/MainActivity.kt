package com.example.vofaz.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.vofaz.Register
import com.example.vofaz.R
import com.example.vofaz.common.model.Database
import com.example.vofaz.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity(), Register.View {

    override lateinit var presenter: Register.Presenter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.mainToolbar


        setSupportActionBar(toolbar)

        supportActionBar?.title = ""

        with(binding) {


        }



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
}