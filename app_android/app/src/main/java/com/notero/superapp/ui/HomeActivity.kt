package com.notero.superapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notero.superapp.R
import com.notero.superapp.data.ListRepository
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: ListaOverviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val rv = findViewById<RecyclerView>(R.id.rvListas)
        adapter = ListaOverviewAdapter(mutableListOf()) { dto ->
            val intent = Intent(this, ListaActivity::class.java)
            intent.putExtra("LISTA_ID", dto.id)
            startActivity(intent)
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabNew)
        fab.setOnClickListener {
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cargarListas()
    }

    private fun cargarListas() {
        lifecycleScope.launch {
            try {
                val listas = ListRepository.todasMisListas()
                adapter.update(listas)
            } catch (e: Exception) {
                android.widget.Toast.makeText(this@HomeActivity, "Error cargando listas", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }
}

