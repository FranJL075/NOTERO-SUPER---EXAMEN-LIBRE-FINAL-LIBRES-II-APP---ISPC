package com.notero.superapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notero.superapp.databinding.ActivityListaDetailBinding

class ListaDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listId = intent.getIntExtra("listId", -1)
        // TODO load list products by id and show images
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

