package com.manda0101.kosantelu.database

import com.manda0101.kosantelu.model.Kosan
import kotlinx.coroutines.flow.Flow

class KosanRepository(private val kosanDao: KosanDao) {

    val allKosans: Flow<List<Kosan>> = kosanDao.getAllKosans()

    suspend fun insert(kosan: Kosan) {
        kosanDao.insert(kosan)
    }

    suspend fun update(kosan: Kosan) {
        kosanDao.update(kosan)
    }

    suspend fun delete(kosan: Kosan) {
        kosanDao.delete(kosan)
    }

    suspend fun markAsDeleted(kosanId: Long) {
        kosanDao.markAsDeleted(kosanId)
    }

    fun getKosanById(id: Long): Flow<Kosan> {
        return kosanDao.getKosanById(id)
    }
}