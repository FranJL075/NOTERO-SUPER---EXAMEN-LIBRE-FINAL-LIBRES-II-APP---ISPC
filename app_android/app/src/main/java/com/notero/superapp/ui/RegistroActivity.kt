package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notero.superapp.databinding.ActivityRegistroBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.RegisterRequest
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()

            lifecycleScope.launch {
                try {
                    ApiService.instance.register(
                        RegisterRequest(email, password, firstName, lastName)
                    )
                    Toast.makeText(this@RegistroActivity, "Registro exitoso, inicia sesión", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegistroActivity, LoginActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@RegistroActivity, "Error al registrarse", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

