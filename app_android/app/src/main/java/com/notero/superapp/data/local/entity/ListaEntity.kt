package com.notero.superapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listas")
data class ListaEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val limite: Float,
    val total: Float,
    val fechaCreacion: Long,
    val detallesJson: String // json serializado de detalles
)
