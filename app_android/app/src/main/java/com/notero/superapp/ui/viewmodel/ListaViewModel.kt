package com.notero.superapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.ListaDto

class ListaViewModel : ViewModel() {
    private val _lista = MutableStateFlow<ListaDto?>(null)
    val lista: StateFlow<ListaDto?> = _lista

    private val currentId = MutableStateFlow<Int?>(null)

    fun load(id: Int) {
        if (currentId.value == id) return
        currentId.value = id
        viewModelScope.launch {
            try {
                val l = ApiService.instance.getList(id)
                _lista.value = l
            } catch (_: Exception) {}
        }
    }

    fun addCodigo(codigo: String) {
        val id = currentId.value ?: return
        viewModelScope.launch {
            try {
                ApiService.instance.addItemsBulk(id, CodigosRequest(listOf(codigo)))
                load(id)
            } catch (_: Exception) {}
        }
    }
}
