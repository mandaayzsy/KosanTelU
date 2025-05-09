package com.manda0101.kosantelu.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recycle_bin")
data class RecycleBin(
    @PrimaryKey val kosan_id: Long,
    val nama: String,
    val alamat: String,
    val harga: String,
    val fasilitas: String
)

