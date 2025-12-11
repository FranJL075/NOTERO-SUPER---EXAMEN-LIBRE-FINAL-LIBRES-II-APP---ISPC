package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.R
import com.notero.superapp.data.UserRepository
import com.notero.superapp.network.AuthInterceptor
import kotlinx.coroutines.launch

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etUbicacion = findViewById<EditText>(R.id.etUbicacion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfil)
        val btnCerrar = findViewById<Button>(R.id.btnCerrarSesion)

        lifecycleScope.launch {
            try {
                val me = UserRepository.me()
                tvEmail.text = me.email
                etNombre.setText(me.nombre ?: "")
                etUbicacion.setText(me.ubicacion ?: "")
            } catch (e: Exception) {
                android.widget.Toast.makeText(this@PerfilActivity, "Error cargando perfil", android.widget.Toast.LENGTH_LONG).show()
            }
        }

        btnGuardar.setOnClickListener {
            val ubicacion = etUbicacion.text.toString()
            lifecycleScope.launch {
                try {
                    UserRepository.updateUbicacion(ubicacion)
                    android.widget.Toast.makeText(this@PerfilActivity, "Perfil actualizado", android.widget.Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    android.widget.Toast.makeText(this@PerfilActivity, "Error actualizando", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }

        btnCerrar.setOnClickListener {
            AuthInterceptor.setToken(null)
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}

