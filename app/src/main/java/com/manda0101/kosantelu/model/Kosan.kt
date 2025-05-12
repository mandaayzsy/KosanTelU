package com.manda0101.kosantelu.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kosan_table")
data class Kosan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val alamat: String,
    val harga: String,
    val fasilitas: String,
    val deleted: Int = 0
)
