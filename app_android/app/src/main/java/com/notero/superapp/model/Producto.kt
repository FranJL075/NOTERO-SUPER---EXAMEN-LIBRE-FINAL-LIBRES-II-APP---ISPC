package com.notero.superapp.model

// Modelo de producto de supermercado

data class Producto(
    val id: Int = 0,
    val nombre: String = "",
    val codigo: String = "",
    var precio: Float = 0f,
    var favorito: Boolean = false
)
