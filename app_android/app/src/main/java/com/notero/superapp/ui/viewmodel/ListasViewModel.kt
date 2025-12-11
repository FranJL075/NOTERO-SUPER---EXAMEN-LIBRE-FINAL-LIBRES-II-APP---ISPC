package com.notero.superapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.notero.superapp.model.Lista
import com.notero.superapp.repository.ListaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListasViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ListaRepository(app)
    private val _listas = MutableStateFlow<List<Lista>>(emptyList())
    val listas: StateFlow<List<Lista>> = _listas

    fun cargar() {
        viewModelScope.launch {
            _listas.value = repository.obtenerListasLocal()
        }
    }
}
