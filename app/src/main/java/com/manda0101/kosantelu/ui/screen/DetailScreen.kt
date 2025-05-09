package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, kosanId: Long) {
    val viewModel: MainViewModel = viewModel()

    // Menggunakan collectAsState untuk mengambil data kosan, dengan nilai default Kosan
    val kosan = viewModel.getKosanById(kosanId).collectAsState(initial = Kosan(0L, "", "", "", "")).value

    // State untuk menyimpan inputan pengguna
    var kosanName by remember { mutableStateOf(kosan.nama) }
    var kosanAlamat by remember { mutableStateOf(kosan.alamat) }
    var kosanHarga by remember { mutableStateOf(kosan.harga) }
    var kosanFasilitas by remember { mutableStateOf(kosan.fasilitas) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Kosan", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF8B0000), // Merah marun
                    titleContentColor = Color.White
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TextField untuk Nama Kosan
            OutlinedTextField(
                value = kosanName,
                onValueChange = { kosanName = it },
                label = { Text("Nama Kosan") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            // TextField untuk Alamat
            OutlinedTextField(
                value = kosanAlamat,
                onValueChange = { kosanAlamat = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            // TextField untuk Harga
            OutlinedTextField(
                value = kosanHarga,
                onValueChange = { kosanHarga = it },
                label = { Text("Harga") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            // TextField untuk Fasilitas
            OutlinedTextField(
                value = kosanFasilitas,
                onValueChange = { kosanFasilitas = it },
                label = { Text("Fasilitas") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            // Tombol untuk menyimpan perubahan, dengan modifier untuk memastikan tombol di tengah
            Button(
                onClick = {
                    val updatedKosan = kosan.copy(
                        nama = kosanName,
                        alamat = kosanAlamat,
                        harga = kosanHarga,
                        fasilitas = kosanFasilitas
                    )
                    viewModel.update(updatedKosan)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth() // Ensure the button takes full width
                    .padding(8.dp) // Add some padding
                    .align(Alignment.CenterHorizontally), // Center the button
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)) // Merah marun
            ) {
                Text("Simpan Perubahan", color = Color.White)
            }
        }
    }
}