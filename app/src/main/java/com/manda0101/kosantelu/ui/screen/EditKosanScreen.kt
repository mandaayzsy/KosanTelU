package com.manda0101.kosantelu.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.firstOrNull
import androidx.compose.foundation.layout.wrapContentWidth // Import untuk wrapContentWidth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKosanScreen(kosanId: Long, viewModel: MainViewModel, navController: NavController) {
    Log.d("EditKosanScreen", "Received kosanId: $kosanId")

    val context = LocalContext.current

    var kosan: com.manda0101.kosantelu.model.Kosan? by remember { mutableStateOf(null) }

    val kosanName = remember { mutableStateOf("") }
    val kosanAlamat = remember { mutableStateOf("") }
    val kosanHarga = remember { mutableStateOf("") }
    val kosanFasilitas = remember { mutableStateOf("") }

    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        Log.d("EditKosanScreen", "Fetching kosan with ID: $kosanId in LaunchedEffect")
        val fetchedKosan = viewModel.getKosanById(kosanId).firstOrNull()

        if (fetchedKosan != null) {
            kosan = fetchedKosan
            kosanName.value = fetchedKosan.nama
            kosanAlamat.value = fetchedKosan.alamat
            kosanHarga.value = fetchedKosan.harga
            kosanFasilitas.value = fetchedKosan.fasilitas
        } else {
            Log.e("EditKosanScreen", "Kosan with ID: $kosanId not found.")
            Toast.makeText(context, "Kosan tidak ditemukan", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Kosan") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (kosan == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
                Text("Memuat data kosan...", modifier = Modifier.padding(top = 80.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextField(
                    value = kosanName.value,
                    onValueChange = { kosanName.value = it },
                    label = { Text("Nama Kosan") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = kosanAlamat.value,
                    onValueChange = { kosanAlamat.value = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = kosanHarga.value,
                    onValueChange = { kosanHarga.value = it },
                    label = { Text("Harga") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = kosanFasilitas.value,
                    onValueChange = { kosanFasilitas.value = it },
                    label = { Text("Fasilitas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            val currentKosan = kosan!!

                            val cleanedHarga = kosanHarga.value.replace("[^0-9]".toRegex(), "")
                            kosanHarga.value = cleanedHarga

                            if (kosanName.value.isBlank() || kosanAlamat.value.isBlank() || cleanedHarga.isBlank() || kosanFasilitas.value.isBlank()) {
                                errorMessage.value = "Semua kolom harus diisi"
                            } else if (!cleanedHarga.matches("^[0-9]+$".toRegex())) {
                                errorMessage.value = "Harga harus berupa angka yang valid"
                            } else {
                                val updatedKosan = currentKosan.copy(
                                    nama = kosanName.value,
                                    alamat = kosanAlamat.value,
                                    harga = cleanedHarga,
                                    fasilitas = kosanFasilitas.value
                                )

                                viewModel.update(updatedKosan)

                                Toast.makeText(context, "Kosan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp)
                    ) {
                        Text("Simpan Perubahan")
                    }
                }
            }
        }
    }
}