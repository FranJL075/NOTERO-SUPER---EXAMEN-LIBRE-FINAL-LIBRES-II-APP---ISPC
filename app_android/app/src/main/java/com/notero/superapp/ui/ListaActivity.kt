package com.notero.superapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notero.superapp.R
import com.notero.superapp.model.DetalleLista
import com.notero.superapp.viewmodel.ListaViewModel

class ListaActivity : AppCompatActivity() {

    private lateinit var viewModel: ListaViewModel
    private lateinit var adapter: DetalleAdapter

    private val scanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val codigo = result.data?.getStringExtra("SCAN_RESULT")
            if (codigo != null) {
                viewModel.agregarPorCodigo(codigo)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        viewModel = ViewModelProvider(this)[ListaViewModel::class.java]

        val rv = findViewById<RecyclerView>(R.id.rvDetalles)
        adapter = DetalleAdapter(mutableListOf()) { detalle: DetalleLista ->
            viewModel.eliminar(detalle)
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvLimite = findViewById<TextView>(R.id.tvLimite)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnLimite = findViewById<Button>(R.id.btnLimite)
        val btnPromo = findViewById<Button>(R.id.btnPromo)

        viewModel.listaActual.observe(this) { lista ->
            if (lista != null) {
                adapter.update(lista.detalles)
                tvTotal.text = "Total: $${"%.2f".format(lista.total)}"
                tvLimite.text = "Límite: $${"%.2f".format(lista.limitePresupuesto)}"
                val color = if (lista.estaSobrePresupuesto())
                    ContextCompat.getColor(this, android.R.color.holo_red_dark)
                else ContextCompat.getColor(this, android.R.color.black)
                tvTotal.setTextColor(color)
            }
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                android.widget.Toast.makeText(this, error, android.widget.Toast.LENGTH_LONG).show()
            }
        }

        // Crear lista inicial si no existe (para demo)
        if (viewModel.listaActual.value == null) {
            viewModel.crearLista("Mi presupuesto", 0f)
        }

        fabAdd.setOnClickListener {
            scanLauncher.launch(Intent(this, AddProductoActivity::class.java))
        }

        btnPromo.setOnClickListener {
            viewModel.aplicarPromo()
        }

        btnGuardar.setOnClickListener {
            android.widget.Toast.makeText(this, "Lista guardada (demo)", android.widget.Toast.LENGTH_SHORT).show()
        }

        btnLimite.setOnClickListener {
            mostrarDialogoLimite()
        }
    }

    private fun mostrarDialogoLimite() {
        val input = android.widget.EditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Establecer límite de presupuesto")
            .setView(input)
            .setPositiveButton("Aceptar") { _, _ ->
                val valor = input.text.toString().toFloatOrNull()
                if (valor != null) viewModel.establecerLimite(valor)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
