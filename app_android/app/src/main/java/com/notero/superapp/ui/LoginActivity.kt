package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notero.superapp.databinding.ActivityLoginBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.AuthInterceptor
import com.notero.superapp.network.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            lifecycleScope.launch {
                try {
                    val response = ApiService.instance.login(LoginRequest(email, password))
                    // Guardar token en SharedPreferences
                    val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                    prefs.edit().putString("token", response.access).apply()
                    // Actualizar interceptor para siguientes requests
                    AuthInterceptor.setToken(response.access)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}

