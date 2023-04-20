package com.example.vofaz.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.vofaz.Login
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
        presenter = LoginPresenter(this)


        with(binding) {
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)

            loginBtnSign.setOnClickListener {

                presenter.login(loginEditEmail.text.toString(), loginEditPassword.text.toString())

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
        // TODO: Go to the next screen
    }

    override fun onUserUnauthenticated(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun goToRegisterScreen() {
        TODO("Not yet implemented")
    }
}
