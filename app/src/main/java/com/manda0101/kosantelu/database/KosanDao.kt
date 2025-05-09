package com.manda0101.kosantelu.database

import androidx.room.*
import com.manda0101.kosantelu.model.Kosan
import kotlinx.coroutines.flow.Flow

@Dao
interface KosanDao {
    @Query("SELECT * FROM kosan_table")
    fun getAllKosans(): Flow<List<Kosan>>

    @Insert
    suspend fun insert(kosan: Kosan)

    @Delete
    suspend fun delete(kosan: Kosan)

    @Query("SELECT * FROM kosan_table WHERE id = :id LIMIT 1")
    fun getKosanById(id: Long): Flow<Kosan>

    @Update
    suspend fun update(kosan: Kosan)
}

