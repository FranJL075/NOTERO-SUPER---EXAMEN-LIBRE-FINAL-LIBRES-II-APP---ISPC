package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.notero.superapp.databinding.ActivityListasBinding
import com.notero.superapp.ui.adapter.ListaAdapter
import com.notero.superapp.ui.viewmodel.ListasViewModel
import com.notero.superapp.util.collectIn

class ListasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListasBinding
    private val viewModel: ListasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = ListaAdapter { lista ->
            val intent = Intent(this, ListaDetailActivity::class.java)
            intent.putExtra("listId", lista.id)
            startActivity(intent)
        }
        binding.recyclerListas.layoutManager = LinearLayoutManager(this)
        binding.recyclerListas.adapter = adapter

        viewModel.listas.collectIn(this) { adapter.setData(it) }
        viewModel.cargar()
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}

