package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val kosans by viewModel.allKosans.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kosan Tel-U") },
                navigationIcon = {
                    IconButton(onClick = { /* handle navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addKosan") }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Tambah Kosan"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(kosans) { kosan ->
                KosanItem(kosan) {
                    navController.navigate("detailScreen/${kosan.id}")
                }
            }
        }
    }
}

@Composable
fun KosanItem(kosan: Kosan, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama Kosan: ${kosan.nama}", color = Color.Black)
            Text(text = "Alamat: ${kosan.alamat}", color = Color.Gray)
            Text(text = "Harga: ${kosan.harga}", color = Color.Gray)
            Text(text = "Fasilitas: ${kosan.fasilitas}", color = Color.Gray)
        }
    }
}
