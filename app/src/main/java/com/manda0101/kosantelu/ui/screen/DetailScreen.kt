package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    val kosan = viewModel.getKosanById(kosanId).collectAsState(initial = Kosan(0L, "", "", "","")).value

    // State untuk menyimpan inputan pengguna
    var kosanName by remember { mutableStateOf(kosan.nama) }
    var kosanAlamat by remember { mutableStateOf(kosan.alamat) }
    var kosanHarga by remember { mutableStateOf(kosan.harga) }
    var kosanFasilitas by remember { mutableStateOf(kosan.fasilitas) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Kosan") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TextFields untuk mengedit data kosan
            TextField(value = kosanName, onValueChange = { kosanName = it }, label = { Text("Nama Kosan") })
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            TextField(value = kosanAlamat, onValueChange = { kosanAlamat = it }, label = { Text("Alamat") })
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            TextField(value = kosanHarga, onValueChange = { kosanHarga = it }, label = { Text("Harga") })
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            TextField(value = kosanFasilitas, onValueChange = { kosanFasilitas = it }, label = { Text("Fasilitas") })
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Tombol untuk menyimpan perubahan
            Button(onClick = {
                // Menyimpan perubahan ke dalam model Kosan
                val updatedKosan = kosan.copy(
                    nama = kosanName,
                    alamat = kosanAlamat,
                    harga = kosanHarga,
                    fasilitas = kosanFasilitas
                )
                viewModel.update(updatedKosan)
                navController.popBackStack()
            }) {
                Text("Simpan Perubahan")
            }
        }
    }
}
