package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notero.superapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnListas.setOnClickListener { startActivity(Intent(this, ListasActivity::class.java)) }
        binding.btnPromos.setOnClickListener { startActivity(Intent(this, PromocionesActivity::class.java)) }
        binding.btnPerfil.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }
        binding.btnImport.setOnClickListener { startActivity(Intent(this, ImportTxtActivity::class.java)) }
    }
}

