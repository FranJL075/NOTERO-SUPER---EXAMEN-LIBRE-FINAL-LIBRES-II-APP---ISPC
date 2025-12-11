package com.notero.superapp.data

import com.notero.superapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {
    private val api = ApiService.instance

    suspend fun me() = withContext(Dispatchers.IO) {
        api.getPerfil()
    }

    suspend fun updateUbicacion(nueva: String) = withContext(Dispatchers.IO) {
        api.updatePerfil(mapOf("ubicacion" to nueva))
    }
}
