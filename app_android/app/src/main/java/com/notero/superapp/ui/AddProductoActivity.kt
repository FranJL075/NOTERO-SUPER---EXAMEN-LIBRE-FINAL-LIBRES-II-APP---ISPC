package com.notero.superapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.notero.superapp.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No necesitamos layout: arrancamos escáner directo
        iniciarScan()
    }

    private fun iniciarScan() {
        IntentIntegrator(this).apply {
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Escanea código de barras o QR")
            setBeepEnabled(true)
            setOrientationLocked(false)
            captureActivity = CaptureAct::class.java // clase vacía que usa el theme
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                buscarProducto(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun buscarProducto(codigo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val lista = ApiService.instance.getProductoPorCodigo(codigo)
                withContext(Dispatchers.Main) {
                    if (lista.isNotEmpty()) {
                        // Devuelve el producto al caller
                        val data = Intent().putExtra("PRODUCTO_ID", lista[0].id)
                        setResult(Activity.RESULT_OK, data)
                        finish()
                    } else {
                        Toast.makeText(this@AddProductoActivity, "Producto no encontrado", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddProductoActivity, "Error consultando API", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}

// Clase necesaria para que ZXing use el tema de la app
class CaptureAct : com.journeyapps.barcodescanner.CaptureActivity()
