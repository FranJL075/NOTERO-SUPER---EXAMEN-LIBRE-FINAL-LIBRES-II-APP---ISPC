package com.notero.superapp.repository

import com.notero.superapp.model.*
import com.notero.superapp.network.ApiService
import com.notero.superapp.network.CodigosRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListaRepository(private val api: ApiService = ApiService.instance) {

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
        runCatching { dtoToLista(api.getList(id)) }.getOrNull()
    }

    suspend fun agregarItems(id: Int, codigos: List<String>) = withContext(Dispatchers.IO) {
        runCatching { api.addItemsBulk(id, CodigosRequest(codigos)) }
    }
}
