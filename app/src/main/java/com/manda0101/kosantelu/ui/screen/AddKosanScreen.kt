package com.manda0101.kosantelu.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.R
import com.manda0101.kosantelu.model.Kosan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddKosanScreen(viewModel: MainViewModel, navController: NavController) {

    val kosanName = remember { mutableStateOf("") }
    val kosanAlamat = remember { mutableStateOf("") }
    val kosanHarga = remember { mutableStateOf("") }
    val kosanFasilitas = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kosan Tel-U") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {  // Tombol Kembali
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp), // Padding di sekeliling
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {

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
                        viewModel.insert(newKosan)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2))
                ) {
                    Text("Simpan Kosan", color = Color.White)
                }
            }
        }
    )
}