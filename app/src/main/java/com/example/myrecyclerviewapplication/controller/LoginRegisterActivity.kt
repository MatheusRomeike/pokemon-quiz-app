package com.example.myrecyclerviewapplication.controller

import android.content.Intent
import com.example.myrecyclerviewapplication.viewmodel.UserViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.myrecyclerviewapplication.R
import com.example.myrecyclerviewapplication.databinding.LoginRegisterViewBinding
import com.example.myrecyclerviewapplication.model.user.User
import com.example.myrecyclerviewapplication.model.user.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    lateinit var binding: LoginRegisterViewBinding
    val viewModel: UserViewModel by viewModels()
    @Inject lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<LoginRegisterViewBinding>(
            this, R.layout.login_register_view
        )

        val user = binding.userEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        val loginButton = binding.loginButton
        loginButton.isEnabled = false
        val registerButton = binding.registerButton
        registerButton.isEnabled = false

        binding.userEditText.addTextChangedListener {
            activateButtons()
        }

        binding.passwordEditText.addTextChangedListener {
            activateButtons()
        }

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.registerButton.setOnClickListener {
            register()
        }

    }

    fun activateButtons() {
        val activate = !(binding.userEditText.text.isNullOrEmpty() || binding.passwordEditText.text.isNullOrEmpty())
        binding.loginButton.isEnabled = activate
        binding.registerButton.isEnabled = activate
    }

    fun register() {
        viewModel.getUserByName(binding.userEditText.toString())
        if (viewModel.userData.value == null) {
            viewModel.insert(User(0, binding.userEditText.text.toString(), binding.passwordEditText.text.toString()))
            Toast.makeText(this, "Registrado com sucesso.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro ao registrar: Usuário já registrado.", Toast.LENGTH_SHORT).show()
        }

    }

    fun login() {
        viewModel.getUserByNameAndPassword(binding.userEditText.text.toString(), binding.passwordEditText.text.toString())
        if (viewModel.userData.value != null) {
            Toast.makeText(this, "Login com sucesso.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, PokemonActivity::class.java))
            finish() // Finaliza esta atividade para que o usuário não possa voltar para a tela de login com o botão de voltar
        } else {
            Toast.makeText(this, "Erro ao entrar: Usuário/Senha inválidos.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

}