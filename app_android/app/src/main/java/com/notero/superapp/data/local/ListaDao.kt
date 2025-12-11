package com.notero.superapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.notero.superapp.data.local.entity.ListaEntity

@Dao
interface ListaDao {
    @Query("SELECT * FROM listas")
    suspend fun getAll(): List<ListaEntity>

    @Query("SELECT * FROM listas WHERE id=:id")
    suspend fun getById(id: Int): ListaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ListaEntity)
}
