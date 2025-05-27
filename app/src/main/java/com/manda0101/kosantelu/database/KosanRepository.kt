package com.manda0101.kosantelu.database

import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class KosanRepository(private val kosanDao: KosanDao, private val recycleBinDao: RecycleBinDao) {

    val allKosans: Flow<List<Kosan>> = kosanDao.getAllKosans()

    fun getKosanById(id: Long): Flow<Kosan?> {
        return kosanDao.getKosanById(id)
    }

    suspend fun insert(kosan: Kosan) {
        withContext(Dispatchers.IO) {
            kosanDao.insert(kosan)
        }
    }

    suspend fun update(kosan: Kosan) {
        withContext(Dispatchers.IO) {
            kosanDao.update(kosan)
        }
    }

    suspend fun delete(kosan: Kosan) {
        withContext(Dispatchers.IO) {
            val recycleBin = RecycleBin(
                kosan_id = kosan.id,
                nama = kosan.nama,
                alamat = kosan.alamat,
                harga = kosan.harga,
                fasilitas = kosan.fasilitas
            )
            recycleBinDao.insert(recycleBin)
            kosanDao.delete(kosan)
        }
    }

    suspend fun restoreKosan(kosan: RecycleBin) {
        withContext(Dispatchers.IO) {
            val restoredKosan = Kosan(
                id = kosan.kosan_id,
                nama = kosan.nama,
                alamat = kosan.alamat,
                harga = kosan.harga,
                fasilitas = kosan.fasilitas
            )
            kosanDao.insert(restoredKosan)
            recycleBinDao.deleteByKosanId(kosan.kosan_id)
        }
    }

    suspend fun permanentlyDeleteKosan(kosan: RecycleBin) {
        withContext(Dispatchers.IO) {
            recycleBinDao.deleteByKosanId(kosan.kosan_id)
        }
    }

    fun getAllDeletedKosans(): Flow<List<RecycleBin>> {
        return recycleBinDao.getAllDeletedKosans()
    }
}
