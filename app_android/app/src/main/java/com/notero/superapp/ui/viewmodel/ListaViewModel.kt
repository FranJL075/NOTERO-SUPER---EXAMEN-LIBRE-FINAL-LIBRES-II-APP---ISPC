package com.notero.superapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notero.superapp.model.Lista
import com.notero.superapp.repository.ListaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListaViewModel(
    private val repository: ListaRepository = ListaRepository()
) : ViewModel() {
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
}
