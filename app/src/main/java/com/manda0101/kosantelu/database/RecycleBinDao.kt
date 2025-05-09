package com.manda0101.kosantelu.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.manda0101.kosantelu.model.RecycleBin
import kotlinx.coroutines.flow.Flow

@Dao
interface RecycleBinDao {
    @Insert
    suspend fun insert(recycleBin: RecycleBin)

    @Delete
    suspend fun delete(recycleBin: RecycleBin)

    @Query("SELECT * FROM recycle_bin")
    fun getAllDeletedKosans(): Flow<List<RecycleBin>>

    @Query("DELETE FROM recycle_bin WHERE kosan_id = :kosanId")
    suspend fun deleteByKosanId(kosanId: Long)
}

