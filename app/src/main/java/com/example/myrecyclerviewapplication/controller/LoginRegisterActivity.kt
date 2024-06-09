package com.example.myrecyclerviewapplication.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myrecyclerviewapplication.compose.LoginRegisterScreen
import com.example.myrecyclerviewapplication.model.user.User
import com.example.myrecyclerviewapplication.model.user.UserRepository
import com.example.myrecyclerviewapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginRegisterActivity : ComponentActivity() {
    val viewModel: UserViewModel by viewModels()
    @Inject lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginRegisterScreen(
                onLoginClick = { user, password -> login(user, password) },
                onRegisterClick = { user, password -> register(user, password) }
            )
        }
    }

    private fun login(user: String, password: String) {
        viewModel.getUserByNameAndPassword(user, password)
        if (viewModel.userData.value != null) {
            Toast.makeText(this, "Login com sucesso.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Erro ao entrar: Usuário/Senha inválidos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(user: String, password: String) {
        viewModel.getUserByName(user)
        if (viewModel.userData.value == null) {
            viewModel.insert(User(0, user, password, current = false))
            Toast.makeText(this, "Registrado com sucesso.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro ao registrar: Usuário já registrado.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}
