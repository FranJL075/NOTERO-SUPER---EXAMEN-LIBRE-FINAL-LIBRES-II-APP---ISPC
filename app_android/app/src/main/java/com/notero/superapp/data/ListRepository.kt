package com.notero.superapp.data

import com.notero.superapp.model.Producto
import com.notero.superapp.model.DetalleLista
import com.notero.superapp.model.Lista
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.CodigosRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ListRepository {

    private val api = ApiService.instance

    suspend fun fetchProductoPorCodigo(codigo: String): Producto? = withContext(Dispatchers.IO) {
        val list = api.getProductoPorCodigo(codigo)
        return@withContext list.firstOrNull()?.let {
            Producto(
                id = it.id,
                nombre = it.name,
                codigo = codigo,
                precio = it.price.toFloat(),
            )
        }
    }

    suspend fun crearLista(nombre: String, limite: Float?): Lista = withContext(Dispatchers.IO) {
        val dto = api.createList(ApiService.CreateListRequest(nombre, limite?.toDouble()))
        return@withContext Lista(
            id = dto.id,
            nombre = dto.nombre,
            limitePresupuesto = dto.limite_presupuesto?.toFloat() ?: 0f,
            detalles = mutableListOf()
        )
    }

    suspend fun addItemsBulk(listaId: Int, codigos: List<String>): List<DetalleLista> = withContext(Dispatchers.IO) {
        val resp = api.addItemsBulk(listaId, CodigosRequest(codigos))
        return@withContext resp.added.map {
            val prod = Producto(it.id, it.name, "", it.price.toFloat(), false)
            DetalleLista(producto = prod, cantidad = 1)
        }
    }

    suspend fun applyPromo(listaId: Int) = withContext(Dispatchers.IO) {
        api.applyPromo(listaId)
    }

    suspend fun todasMisListas() = withContext(Dispatchers.IO) {
        api.getLists()
    }

    suspend fun obtenerLista(id: Int) = withContext(Dispatchers.IO) {
        api.getList(id)
    }

    suspend fun guardarLista(lista: Lista) = withContext(Dispatchers.IO) {
        api.patchList(lista.id, mapOf(
            "limite_presupuesto" to lista.limitePresupuesto.toDouble()
        ))
    }

    suspend fun obtenerNegocios() = withContext(Dispatchers.IO) {
        api.getNegocios()
    }

    suspend fun asignarNegocio(listaId: Int, negocioId: Int) = withContext(Dispatchers.IO) {
        api.patchList(listaId, mapOf("negocio" to negocioId))
    }
}
