package com.notero.superapp.model

// Negocio que ofrece promociones a una lista de compras

data class NegocioPromocionado(
    val id: Int = 0,
    val nombre: String = "",
    val descuento: Float = 0f, // porcentaje ej 0.1 = 10%
    val presupuestoBase: Float = 0f,
    val direccion: String = ""
) {
    fun aplicarDescuento(totalLista: Float): Float = totalLista * (1 - descuento)
}
