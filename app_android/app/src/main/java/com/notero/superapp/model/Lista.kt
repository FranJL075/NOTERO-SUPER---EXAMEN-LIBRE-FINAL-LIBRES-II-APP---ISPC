package com.notero.superapp.model

import java.util.Date

// Lista de compras/presupuesto

data class Lista(
    val id: Int = 0,
    val nombre: String = "",
    var limitePresupuesto: Float = 0f,
    val fechaCreacion: Date = Date(),
    val detalles: MutableList<DetalleLista> = mutableListOf()
) {
    val total: Float
        get() = detalles.sumOf { it.subtotal }

    fun agregarProducto(detalle: DetalleLista) {
        detalles.add(detalle)
    }

    fun eliminarProducto(detalle: DetalleLista) {
        detalles.remove(detalle)
    }
}
