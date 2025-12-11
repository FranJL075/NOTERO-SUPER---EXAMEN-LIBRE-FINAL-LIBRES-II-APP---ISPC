package com.notero.superapp.model

// Representa los datos básicos del usuario en la aplicación
// Mantiene la identidad sencilla y se expande según necesidades futuras

data class Usuario(
    val id: Int = 0,
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val ubicacion: String = ""
)
