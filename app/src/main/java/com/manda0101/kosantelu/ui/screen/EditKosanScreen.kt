package com.manda0101.kosantelu.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.model.Kosan

@Composable
fun EditKosanScreen(kosanId: Long, viewModel: MainViewModel, navController: NavController) {
    val kosan = viewModel.getKosanById(kosanId).collectAsState(initial = Kosan(0L, "", "", "", "")).value

    if (kosan.id == 0L) {
        LaunchedEffect(Unit) {
            Toast.makeText(navController.context, "Kosan tidak ditemukan", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
        return
    }

    val kosanName = remember { mutableStateOf(kosan.nama) }
    val kosanAlamat = remember { mutableStateOf(kosan.alamat) }
    val kosanHarga = remember { mutableStateOf(kosan.harga) }
    val kosanFasilitas = remember { mutableStateOf(kosan.fasilitas) }

    val errorMessage = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = kosanName.value, onValueChange = { kosanName.value = it }, label = { Text("Nama Kosan") })

        TextField(value = kosanAlamat.value, onValueChange = { kosanAlamat.value = it }, label = { Text("Alamat") })

        TextField(value = kosanHarga.value, onValueChange = { kosanHarga.value = it }, label = { Text("Harga") })

        TextField(value = kosanFasilitas.value, onValueChange = { kosanFasilitas.value = it }, label = { Text("Fasilitas") })

        if (errorMessage.value.isNotEmpty()) {
            Text(text = errorMessage.value, color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(onClick = {
            if (kosanName.value.isBlank() || kosanAlamat.value.isBlank() || kosanHarga.value.isBlank() || kosanFasilitas.value.isBlank()) {
                errorMessage.value = "Semua kolom harus diisi"
            } else if (!kosanHarga.value.matches("^[0-9]+$".toRegex())) {
                errorMessage.value = "Harga harus berupa angka yang valid"
            } else {
                val updatedKosan = kosan.copy(
                    nama = kosanName.value,
                    alamat = kosanAlamat.value,
                    harga = kosanHarga.value,
                    fasilitas = kosanFasilitas.value
                )

                viewModel.update(updatedKosan)

                navController.popBackStack()
            }
        }) {
            Text("Simpan Perubahan")
        }
    }
}