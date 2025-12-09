package com.notero.superapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notero.superapp.databinding.ActivityPromocionesBinding

class PromocionesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromocionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromocionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO load promos
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

