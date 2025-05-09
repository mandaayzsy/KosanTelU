package com.manda0101.kosantelu.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

// File: AddKosanScreen.kt

@Composable
fun AddKosanScreen(viewModel: MainViewModel, navController: NavController) {
    // Proper use of remember and mutableStateOf
    val kosanName = remember { mutableStateOf("") }
    val kosanAlamat = remember { mutableStateOf("") }
    val kosanHarga = remember { mutableStateOf("") }
    val kosanFasilitas = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = kosanName.value,
            onValueChange = { kosanName.value = it },
            label = { Text("Nama Kosan") }
        )
        TextField(
            value = kosanAlamat.value,
            onValueChange = { kosanAlamat.value = it },
            label = { Text("Alamat") }
        )
        TextField(
            value = kosanHarga.value,
            onValueChange = { kosanHarga.value = it },
            label = { Text("Harga") }
        )
        TextField(
            value = kosanFasilitas.value,
            onValueChange = { kosanFasilitas.value = it },
            label = { Text("Fasilitas") }
        )

        Button(onClick = {
            // Cek validitas input
            if (kosanName.value.isBlank() || kosanAlamat.value.isBlank() || kosanHarga.value.isBlank()) {
                Toast.makeText(navController.context, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return@Button
            }

            val newKosan = Kosan(
                nama = kosanName.value,
                alamat = kosanAlamat.value,
                harga = kosanHarga.value,
                fasilitas = kosanFasilitas.value
            )
            viewModel.insert(newKosan) // Menyimpan kosan baru
            navController.popBackStack() // Kembali ke layar sebelumnya
        }) {
            Text("Simpan Kosan")
        }
    }
}