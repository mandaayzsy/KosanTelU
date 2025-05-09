package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@Composable
fun EditKosanScreen(kosanId: Long, viewModel: MainViewModel, navController: NavController) {
    // Mengambil data kosan berdasarkan ID menggunakan collectAsState
    val kosan = viewModel.getKosanById(kosanId).collectAsState(initial = Kosan(0L, "", "", "", "")).value

    // Cek jika kosanId invalid atau data tidak ditemukan
    if (kosan.id == 0L) {
        Text("Data kosan tidak ditemukan")
        return
    }

    // State untuk input sementara
    val kosanName = remember { mutableStateOf(kosan.nama) }
    val kosanAlamat = remember { mutableStateOf(kosan.alamat) }
    val kosanHarga = remember { mutableStateOf(kosan.harga) }
    val kosanFasilitas = remember { mutableStateOf(kosan.fasilitas) }

    // State untuk pesan error
    val errorMessage = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // TextField untuk Nama Kosan
        TextField(
            value = kosanName.value,
            onValueChange = { kosanName.value = it },
            label = { Text("Nama Kosan") }
        )

        // TextField untuk Alamat
        TextField(
            value = kosanAlamat.value,
            onValueChange = { kosanAlamat.value = it },
            label = { Text("Alamat") }
        )

        // TextField untuk Harga
        TextField(
            value = kosanHarga.value,
            onValueChange = { kosanHarga.value = it },
            label = { Text("Harga") }
        )

        // TextField untuk Fasilitas
        TextField(
            value = kosanFasilitas.value,
            onValueChange = { kosanFasilitas.value = it },
            label = { Text("Fasilitas") }
        )

        // Menampilkan error message jika ada
        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = androidx.compose.ui.graphics.Color.Red
            )
        }

        // Button untuk menyimpan perubahan
        Button(
            onClick = {
                // Sanity check untuk input
                if (kosanName.value.isBlank() || kosanAlamat.value.isBlank() || kosanHarga.value.isBlank() || kosanFasilitas.value.isBlank()) {
                    errorMessage.value = "Semua kolom harus diisi"
                } else if (!kosanHarga.value.matches("^[0-9]+$".toRegex())) {
                    // Validasi harga harus berupa angka
                    errorMessage.value = "Harga harus berupa angka yang valid"
                } else {
                    // Update Kosan data
                    val updatedKosan = kosan.copy(
                        nama = kosanName.value,
                        alamat = kosanAlamat.value,
                        harga = kosanHarga.value,
                        fasilitas = kosanFasilitas.value
                    )
                    // Memperbarui data kosan di repository
                    viewModel.update(updatedKosan)

                    // Kembali ke layar sebelumnya setelah update
                    navController.popBackStack()
                }
            }
        ) {
            Text("Simpan Perubahan")
        }
    }
}
