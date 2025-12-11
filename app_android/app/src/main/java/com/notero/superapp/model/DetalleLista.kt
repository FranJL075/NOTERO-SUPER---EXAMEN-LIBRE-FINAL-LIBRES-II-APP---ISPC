package com.notero.superapp.model

// Representa la línea de detalle dentro de una lista de presupuesto

data class DetalleLista(
    val id: Int = 0,
    val producto: Producto,
    var cantidad: Int = 1,
    val precioUnitario: Float = producto.precio
) {
    val subtotal: Float
        get() = precioUnitario * cantidad
}
