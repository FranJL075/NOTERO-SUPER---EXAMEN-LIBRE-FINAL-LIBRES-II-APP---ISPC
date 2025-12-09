package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.databinding.ActivityListasBinding
import com.notero.superapp.network.ApiService
import kotlinx.coroutines.launch

class ListasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Fetch lists demonstration
        lifecycleScope.launch {
            // TODO fetch lists from backend
        }

        // On item click open detail
        // placeholder
        binding.root.setOnClickListener {
            val intent = Intent(this, ListaDetailActivity::class.java)
            intent.putExtra("listId", 1)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

