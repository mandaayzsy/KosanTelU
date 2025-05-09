package com.manda0101.kosantelu.database

import androidx.room.*
import com.manda0101.kosantelu.model.Kosan
import kotlinx.coroutines.flow.Flow

@Dao
interface KosanDao {

    @Insert
    suspend fun insert(kosan: Kosan)

    @Update
    suspend fun update(kosan: Kosan)

    @Delete
    suspend fun delete(kosan: Kosan)

    @Query("UPDATE kosan_table SET deleted = 1 WHERE id = :id")
    suspend fun markAsDeleted(id: Long)

    @Query("SELECT * FROM kosan_table WHERE deleted = 0 ORDER BY id ASC")
    fun getAllKosans(): Flow<List<Kosan>>

    @Query("SELECT * FROM kosan_table WHERE id = :id AND deleted = 0")
    fun getKosanById(id: Long): Flow<Kosan>
}