package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, kosanId: Long) {
    val viewModel: MainViewModel = viewModel()
    val kosan = viewModel.getKosanById(kosanId)

    // Using mutable state for the TextField values to be editable
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
                        // Correctly use ArrowBack icon
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red, // Red as the color for the top app bar
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
            // Name Field with Divider
            TextField(
                value = kosanName,
                onValueChange = { kosanName = it },
                label = { Text("Nama Kosan") },
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()

            // Address Field with Divider
            TextField(
                value = kosanAlamat,
                onValueChange = { kosanAlamat = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()

            // Price Field with Divider
            TextField(
                value = kosanHarga,
                onValueChange = { kosanHarga = it },
                label = { Text("Harga") },
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()

            // Facilities Field with Divider
            TextField(
                value = kosanFasilitas,
                onValueChange = { kosanFasilitas = it },
                label = { Text("Fasilitas") },
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()

            // Save Button
            Button(
                onClick = {
                    // Save changes or add logic for saving
                    kosan.nama = kosanName
                    kosan.alamat = kosanAlamat
                    kosan.harga = kosanHarga
                    kosan.fasilitas = kosanFasilitas
                    navController.popBackStack()  // Navigate back after saving
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}

@Composable
fun HorizontalDivider() {
    Divider(modifier = Modifier.padding(vertical = 8.dp))
}

@Preview
@Composable
fun PreviewDetailScreen() {
    // Provide a test navController and kosanId here for preview
    DetailScreen(navController = rememberNavController(), kosanId = 1L)
}