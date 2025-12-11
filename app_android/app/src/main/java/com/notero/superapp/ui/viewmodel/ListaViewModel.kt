package com.notero.superapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.notero.superapp.model.Lista
import com.notero.superapp.repository.ListaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListaViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ListaRepository(app)
    private val _lista = MutableStateFlow<Lista?>(null)
    val lista: StateFlow<Lista?> = _lista

    private val currentId = MutableStateFlow<Int?>(null)

    fun load(id: Int) {
        if (currentId.value == id) return
        currentId.value = id
        viewModelScope.launch {
            _lista.value = repository.obtenerLista(id)
        }
    }

    fun agregarCodigo(codigo: String) {
        val id = currentId.value ?: return
        viewModelScope.launch {
            repository.agregarItems(id, listOf(codigo))
            load(id) // recargar
        }
    }

    fun toggleFavorito(productId: Int) {
        val lista = _lista.value ?: return
        lista.toggleFavorito(productId)
        _lista.value = lista.copy() // trigger observers
        viewModelScope.launch { repository.actualizarLista(lista) }
    }

    fun establecerLimite(nuevoLimite: Float) {
        val lista = _lista.value ?: return
        lista.limitePresupuesto = nuevoLimite
        _lista.value = lista.copy()
        viewModelScope.launch { repository.actualizarLista(lista) }
    }
}
