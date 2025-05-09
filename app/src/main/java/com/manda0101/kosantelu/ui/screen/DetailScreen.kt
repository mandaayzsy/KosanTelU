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

// File: DetailScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, kosanId: Long)  {
    val viewModel: MainViewModel = viewModel()
    val kosan by viewModel.getKosanById(kosanId).collectAsState(initial = Kosan(0L, "", "", "", ""))
    var showDialog by remember { mutableStateOf(false) }

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
                    containerColor = Color(0xFF8B0000),
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
            // Menampilkan detail kosan langsung dari objek kosan
            Text(text = "Nama Kosan: ${kosan.nama}", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Alamat: ${kosan.alamat}")
            Text(text = "Harga: ${kosan.harga}")
            Text(text = "Fasilitas: ${kosan.fasilitas}")

            // Tombol untuk edit
            Button(
                onClick = {
                    navController.navigate("editKosan/${kosan.id}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
            ) {
                Text("Edit Kosan", color = Color.White)
            }

            // Tombol untuk memicu tampilan AlertDialog
            Button(
                onClick = { showDialog = true }, // Ubah state menjadi true saat tombol diklik
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
            ) {
                Text("Hapus Kosan", color = Color.White)
            }

            // Menampilkan AlertDialog secara kondisional
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false }, // Ubah state menjadi false saat dialog ditutup
                    title = { Text("Konfirmasi") },
                    text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.delete(kosan)
                            navController.popBackStack()
                            showDialog = false // Tutup dialog setelah menghapus
                        }) {
                            Text("Hapus")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) // Ubah state menjadi false saat tombol batal diklik
                        {
                            Text("Batal")
                        }
                    }
                )
            }
        }
    }
}