package com.example.vofaz.register.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vofaz.common.base.DependencyInjector
import com.example.vofaz.common.util.TextWatcher
import com.example.vofaz.databinding.ActivityRegisterBinding
import com.example.vofaz.main.view.MainActivity
import com.example.vofaz.register.Register

class RegisterActivity: AppCompatActivity(), Register.View {
    override lateinit var presenter: Register.Presenter
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DependencyInjector.registerPresenter(this)

        with(binding) {
            registerEditFullName.addTextChangedListener(watcher)
            registerEditEmail.addTextChangedListener(watcher)
            registerEditPassword.addTextChangedListener(watcher)
            registerEditConfirmPassword.addTextChangedListener(watcher)

            registerBtnCreate.setOnClickListener {
                val name = registerEditFullName.text.toString()
                val email = registerEditEmail.text.toString()
                val password = registerEditPassword.text.toString()
                val confirm = registerEditConfirmPassword.text.toString()

                presenter.create(name, email, password, confirm)
            }
            registerBtnLogin.setOnClickListener {
                finish()
            }
        }
    }

    private val watcher = TextWatcher {
        with(binding) {
            registerBtnCreate.isEnabled =
                       registerEditFullName.text.toString().isNotEmpty()
                    && registerEditEmail.text.toString().isNotEmpty()
                    && registerEditPassword.text.toString().isNotEmpty()
                    && registerEditConfirmPassword.text.toString().isNotEmpty()
        }
    }
    override fun showProgress(enabled: Boolean) {
        binding.registerBtnCreate.showProgress(true)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding.registerEditLayoutFullName.error = nameError?.let { getString(it) }
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.registerEditLayoutEmail.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.registerEditLayoutPassword.error = passwordError?.let { getString(it) }
    }

    override fun displayPasswordNotEquals(notEqualsError: Int?) {
        binding.registerEditLayoutConfirmPassword.error = notEqualsError?.let { getString(it) }

    }

    override fun onUserNotCreated(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onUserCreated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}