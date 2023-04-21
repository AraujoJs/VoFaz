package com.example.vofaz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.vofaz.Login
import com.example.vofaz.common.base.DependencyInjector
import com.example.vofaz.common.util.TextWatcher

import com.example.vofaz.databinding.ActivityLoginBinding
import com.example.vofaz.presentation.LoginPresenter

class LoginActivity : AppCompatActivity(), Login.View {
    override lateinit var presenter: Login.Presenter

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DependencyInjector.loginPresenter(this)


        with(binding) {
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)

            loginBtnSign.setOnClickListener {

                presenter.login(loginEditEmail.text.toString(), loginEditPassword.text.toString())

            }
            loginBtnCreate.setOnClickListener {
                goToRegisterScreen()
            }

        }

    }
    private val watcher = TextWatcher {
        binding.loginBtnSign.isEnabled = binding.loginEditEmail.text.toString().isNotEmpty()
                && binding.loginEditPassword.text.toString().isNotEmpty()
        binding.loginEditLayoutEmail.error = ""
        binding.loginEditLayoutPassword.error = ""
    }

    override fun showProgress(enabled: Boolean) {
        binding.loginBtnSign.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.loginEditLayoutEmail.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditLayoutPassword.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

    }

    override fun onUserUnauthenticated(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun goToRegisterScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
