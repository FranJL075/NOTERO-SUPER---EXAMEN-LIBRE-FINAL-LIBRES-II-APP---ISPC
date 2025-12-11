package com.notero.superapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notero.superapp.model.NegocioPromocionado
import com.notero.superapp.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PromosViewModel(
    private val api: ApiService = ApiService.instance
) : ViewModel() {
    private val _negocios = MutableStateFlow<List<NegocioPromocionado>>(emptyList())
    val negocios: StateFlow<List<NegocioPromocionado>> = _negocios

    init {
        viewModelScope.launch {
            runCatching { api.getNegocios() }.onSuccess { listDto ->
                _negocios.value = listDto.map {
                    NegocioPromocionado(
                        id = it.id,
                        nombre = it.nombre,
                        descuento = it.descuento.toFloat()
                    )
                }
            }
        }
    }
}
