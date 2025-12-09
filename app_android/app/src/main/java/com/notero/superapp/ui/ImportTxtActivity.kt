package com.notero.superapp.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.databinding.ActivityImportTxtBinding
import com.notero.superapp.network.ApiService
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class ImportTxtActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImportTxtBinding
    private val launchPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { processFile(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportTxtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickFile.setOnClickListener { launchPicker.launch("text/plain") }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun processFile(uri: Uri) {
        val items = mutableListOf<String>()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
                val regex = Regex("^[A-Za-z0-9]{3,128}$")
                var count = 0
                lines.forEach { line ->
                    val trimmed = line.trim()
                    if (trimmed.isNotBlank() && regex.matches(trimmed)) {
                        items.add(trimmed)
                        count++
                    }
                    if (count >= 1000) return@forEach  // Límite de 1000 ítems
                }
            }
        }
        lifecycleScope.launch {
            try {
                val response = ApiService.instance.addItemsBulk(ApiService.BulkItemsRequest(items))
                // TODO display added and missing lists
            } catch (e: Exception) {
                Toast.makeText(this@ImportTxtActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}

