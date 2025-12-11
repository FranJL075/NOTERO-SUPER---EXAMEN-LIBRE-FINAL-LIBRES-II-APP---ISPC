package com.notero.superapp.repository

import android.content.Context
import com.google.gson.Gson
import com.notero.superapp.data.local.AppDatabase
import com.notero.superapp.data.local.entity.ListaEntity
import com.notero.superapp.model.*
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.CodigosRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListaRepository(
    context: Context? = null,
    private val api: ApiService = ApiService.instance
) {
    private val gson = Gson()
    private val dao = context?.let { AppDatabase.get(it).listaDao() }

    private fun dtoToProducto(dto: com.notero.superapp.network.ProductDto): Producto =
        Producto(
            id = dto.id,
            nombre = dto.name,
            codigo = "", // el backend no devuelve codigo aquí
            precio = dto.price.toFloat()
        )

    private fun dtoToDetalle(dto: com.notero.superapp.network.DetalleDto): DetalleLista =
        DetalleLista(
            id = dto.id,
            producto = dtoToProducto(dto.producto),
            cantidad = dto.cantidad
        )

    private fun dtoToLista(dto: com.notero.superapp.network.ListaDto): Lista =
        Lista(
            id = dto.id,
            nombre = dto.nombre,
            limitePresupuesto = dto.limite_presupuesto?.toFloat() ?: 0f,
            detalles = dto.detalles.map { dtoToDetalle(it) }.toMutableList()
        )

    suspend fun obtenerLista(id: Int): Lista? = withContext(Dispatchers.IO) {
        // primero intentamos red
        val remote = runCatching { dtoToLista(api.getList(id)) }.getOrNull()
        if (remote != null) {
            // cachear
            dao?.insert(listToEntity(remote))
            return@withContext remote
        }
        // fallback cache
        dao?.getById(id)?.let { entityToList(it) }
    }

    suspend fun agregarItems(id: Int, codigos: List<String>) = withContext(Dispatchers.IO) {
        runCatching { api.addItemsBulk(id, CodigosRequest(codigos)) }
    }

    private fun listToEntity(lista: Lista): ListaEntity = ListaEntity(
        id = lista.id,
        nombre = lista.nombre,
        limite = lista.limitePresupuesto,
        total = lista.total,
        fechaCreacion = lista.fechaCreacion.time,
        detallesJson = gson.toJson(lista.detalles)
    )

    private fun entityToList(entity: ListaEntity): Lista = Lista(
        id = entity.id,
        nombre = entity.nombre,
        limitePresupuesto = entity.limite,
        detalles = gson.fromJson(entity.detallesJson, Array<DetalleLista>::class.java).toMutableList(),
        fechaCreacion = java.util.Date(entity.fechaCreacion)
    )
}
