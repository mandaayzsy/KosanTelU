package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.R
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val kosans by viewModel.allKosans.collectAsState(initial = emptyList())
    val deletedKosans by viewModel.allDeletedKosans.collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var kosanToDelete by remember { mutableStateOf<Kosan?>(null) }

    // State for toggling between list and grid view
    var isGridView by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kosan Tel-U") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addKosan") }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Tambah Kosan")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Toggle Button to switch between List and Grid view
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // List View button with an icon
                Button(
                    onClick = { isGridView = false },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_view_list_24), contentDescription = "List View")
                    Text("List View")
                }

                // Grid View button with an icon
                Button(
                    onClick = { isGridView = true },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_grid_view_24), contentDescription = "Grid View")
                    Text("Grid View")
                }
            }

            // Display kosans in List or Grid based on the selected view
            if (isGridView) {
                // Grid View
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Grid with 2 columns
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(kosans) { kosan ->
                        KosanItem(
                            kosan,
                            onEditClick = { navController.navigate("editKosan/${kosan.id}") },
                            onDeleteClick = {
                                kosanToDelete = kosan
                                showDialog = true
                            }
                        )
                    }
                }
            } else {
                // List View
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(kosans) { kosan ->
                        KosanItem(
                            kosan,
                            onEditClick = { navController.navigate("editKosan/${kosan.id}") },
                            onDeleteClick = {
                                kosanToDelete = kosan
                                showDialog = true
                            }
                        )
                    }
                }
            }

            // Display Deleted Kosans (Recycle Bin)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(deletedKosans) { recycleBin ->
                    RecycleBinItem(
                        recycleBin = recycleBin,
                        viewModel = viewModel,
                        onRestoreClick = { viewModel.restoreKosan(recycleBin) },
                        onDeletePermanentlyClick = { viewModel.permanentlyDeleteKosan(recycleBin) }
                    )
                }
            }

            // Dialog to confirm delete
            if (showDialog && kosanToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Konfirmasi Hapus") },
                    text = { Text("Apakah Anda yakin ingin menghapus kosan ini?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                kosanToDelete?.let {
                                    viewModel.delete(it) // Move to Recycle Bin and delete
                                }
                                showDialog = false
                            }
                        ) {
                            Text("Hapus")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Batal")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun KosanItem(kosan: Kosan, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEditClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama Kosan: ${kosan.nama}", color = Color.Black)
            Text(text = "Alamat: ${kosan.alamat}", color = Color.Gray)
            Text(text = "Harga: ${kosan.harga}", color = Color.Gray)
            Text(text = "Fasilitas: ${kosan.fasilitas}", color = Color.Gray)

            Button(onClick = onEditClick) {
                Text("Edit")
            }

            Button(onClick = onDeleteClick) {
                Text("Hapus")
            }
        }
    }
}

@Composable
fun RecycleBinItem(
    recycleBin: RecycleBin,
    viewModel: MainViewModel,
    onRestoreClick: (RecycleBin) -> Unit,
    onDeletePermanentlyClick: (RecycleBin) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama Kosan: ${recycleBin.nama}", color = Color.Black)
            Text(text = "Alamat: ${recycleBin.alamat}", color = Color.Gray)
            Text(text = "Harga: ${recycleBin.harga}", color = Color.Gray)
            Text(text = "Fasilitas: ${recycleBin.fasilitas}", color = Color.Gray)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onRestoreClick(recycleBin) }) {
                    Text("Pulihkan")
                }
                Button(onClick = { onDeletePermanentlyClick(recycleBin) }) {
                    Text("Hapus Permanen")
                }
            }
        }
    }
}