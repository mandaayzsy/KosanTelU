package com.manda0101.kosantelu.database

import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin
import kotlinx.coroutines.flow.Flow

class KosanRepository(private val kosanDao: KosanDao, private val recycleBinDao: RecycleBinDao) {

    // Flow untuk mengambil semua data Kosan
    val allKosans: Flow<List<Kosan>> = kosanDao.getAllKosans()

    // Menambahkan Kosan baru ke dalam tabel Kosan
    suspend fun insert(kosan: Kosan) {
        kosanDao.insert(kosan)
    }

    // Memperbarui data Kosan yang ada
    suspend fun update(kosan: Kosan) {
        kosanDao.update(kosan)
    }

    // Menghapus Kosan (memindahkannya ke recycle bin)
    suspend fun delete(kosan: Kosan) {
        val recycleBin = RecycleBin(
            kosan_id = kosan.id,
            nama = kosan.nama,
            alamat = kosan.alamat,
            harga = kosan.harga,
            fasilitas = kosan.fasilitas
        )
        // Memasukkan Kosan yang dihapus ke dalam RecycleBin
        recycleBinDao.insert(recycleBin)

        // Menghapus Kosan dari tabel utama
        kosanDao.delete(kosan)
    }

    // Mengembalikan Kosan dari recycle bin ke tabel Kosan
    suspend fun restoreKosan(kosan: RecycleBin) {
        val restoredKosan = Kosan(
            id = kosan.kosan_id,
            nama = kosan.nama,
            alamat = kosan.alamat,
            harga = kosan.harga,
            fasilitas = kosan.fasilitas
        )
        // Menambahkan Kosan yang dipulihkan ke tabel Kosan
        kosanDao.insert(restoredKosan)

        // Menghapus Kosan dari RecycleBin
        recycleBinDao.deleteByKosanId(kosan.kosan_id)
    }

    // Menghapus Kosan secara permanen dari RecycleBin
    suspend fun permanentlyDeleteKosan(kosan: RecycleBin) {
        recycleBinDao.deleteByKosanId(kosan.kosan_id) // Menghapus secara permanen dari RecycleBin
    }

    // Mendapatkan semua Kosan yang ada di RecycleBin (data yang dihapus)
    fun getAllDeletedKosans(): Flow<List<RecycleBin>> {
        return recycleBinDao.getAllDeletedKosans()
    }

    // Mendapatkan Kosan berdasarkan ID
    fun getKosanById(id: Long): Flow<Kosan> {
        return kosanDao.getKosanById(id)
    }
}
