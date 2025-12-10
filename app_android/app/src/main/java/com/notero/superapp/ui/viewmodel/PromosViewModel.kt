package com.notero.superapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.NegocioDto

class PromosViewModel : ViewModel() {
    private val _negocios = MutableStateFlow<List<NegocioDto>>(emptyList())
    val negocios: StateFlow<List<NegocioDto>> = _negocios

    init {
        viewModelScope.launch {
            try {
                _negocios.value = ApiService.instance.getNegocios()
            } catch (_: Exception) {}
        }
    }
}
