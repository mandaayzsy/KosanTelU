package com.manda0101.kosantelu.ui.screen

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manda0101.kosantelu.R
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val kosans by viewModel.allKosans.collectAsState(initial = emptyList())
    val deletedKosans by viewModel.allDeletedKosans.collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var kosanToDelete by remember { mutableStateOf<Any?>(null) }

    val isGridView by viewModel.currentLayoutPreference.collectAsState(initial = false)
    var showRecycleBin by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Log.d("KosanTeluUI", "MainScreen recomposed. isGridView = $isGridView, showRecycleBin = $showRecycleBin")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kosan Tel-U") },
                actions = {
                    IconButton(onClick = {
                        val newIsGridView = !isGridView
                        scope.launch {
                            viewModel.saveLayoutPreference(newIsGridView)
                            Log.d("KosanTeluUI", "MainScreen: Toggling view to isGridView=$newIsGridView")
                        }
                    }) {
                        val icon = if (isGridView) {
                            painterResource(id = R.drawable.baseline_view_list_24)
                        } else {
                            painterResource(id = R.drawable.baseline_grid_view_24)
                        }
                        Icon(painter = icon, contentDescription = stringResource(R.string.toggle_view))
                    }

                    IconButton(onClick = { showRecycleBin = !showRecycleBin }) {
                        val icon = if (showRecycleBin) {
                            painterResource(id = R.drawable.baseline_restore_from_trash_24)
                        } else {
                            painterResource(id = R.drawable.baseline_delete_24)
                        }
                        Icon(painter = icon, contentDescription = stringResource(R.string.recycle_bin_toggle))
                    }
                },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
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
            if (showRecycleBin) {
                Log.d("KosanTeluUI", "MainScreen: Displaying Recycle Bin.")
                if (isGridView) {
                    Log.d("KosanTeluUI", "MainScreen: Recycle Bin Grid View.")
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(deletedKosans) { recycleBin ->
                            RecycleBinItem(
                                recycleBin = recycleBin,
                                onRestoreClick = { viewModel.restoreKosan(recycleBin) },
                                onDeletePermanentlyClick = { kosanToDelete = recycleBin; showDialog = true }
                            )
                        }
                    }
                } else {
                    Log.d("KosanTeluUI", "MainScreen: Recycle Bin List View.")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(deletedKosans) { recycleBin ->
                            RecycleBinItem(
                                recycleBin = recycleBin,
                                onRestoreClick = { viewModel.restoreKosan(recycleBin) },
                                onDeletePermanentlyClick = { kosanToDelete = recycleBin; showDialog = true }
                            )
                        }
                    }
                }
            } else {
                Log.d("KosanTeluUI", "MainScreen: Displaying Main Kosan List/Grid.")
                if (isGridView) {
                    Log.d("KosanTeluUI", "MainScreen: Main Kosan Grid View.")
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(kosans) { kosan ->
                            KosanItem(
                                kosan,
                                onEditClick = {
                                    if (kosan.id == 0L) {
                                        Toast.makeText(context, "ID Kosan tidak valid.", Toast.LENGTH_SHORT).show()
                                        return@KosanItem
                                    }
                                    navController.navigate("editKosan/${kosan.id}")
                                },
                                onDeleteClick = {
                                    kosanToDelete = kosan
                                    showDialog = true
                                }
                            )
                        }
                    }
                } else {
                    Log.d("KosanTeluUI", "MainScreen: Main Kosan List View.")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(kosans) { kosan ->
                            KosanItem(
                                kosan,
                                onEditClick = {
                                    if (kosan.id == 0L) {
                                        Toast.makeText(context, "ID Kosan tidak valid.", Toast.LENGTH_SHORT).show()
                                        return@KosanItem
                                    }
                                    navController.navigate("editKosan/${kosan.id}")
                                },
                                onDeleteClick = {
                                    kosanToDelete = kosan
                                    showDialog = true
                                }
                            )
                        }
                    }
                }
            }

            if (showDialog && kosanToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Konfirmasi Hapus") },
                    text = { Text("Apakah Anda yakin ingin menghapus kosan ini?") },
                    confirmButton = {
                        Button(onClick = {
                            when (val item = kosanToDelete) {
                                is Kosan -> viewModel.delete(item)
                                is RecycleBin -> viewModel.permanentlyDeleteKosan(item)
                            }
                            showDialog = false
                        }) {
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
fun RecycleBinItem(
    recycleBin: RecycleBin,
    onRestoreClick: (RecycleBin) -> Unit,
    onDeletePermanentlyClick: (RecycleBin) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama Kosan: ${recycleBin.nama}", color = Color.Black)
            Text(text = "Alamat: ${recycleBin.alamat}", color = Color.Gray)
            Text(text = "Harga: ${recycleBin.harga}", color = Color.Gray)
            Text(text = "Fasilitas: ${recycleBin.fasilitas}", color = Color.Gray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onRestoreClick(recycleBin) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pulihkan")
                }

                Button(
                    onClick = { onDeletePermanentlyClick(recycleBin) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Hapus Permanen")
                }
            }
        }
    }
}

@Composable
fun KosanItem(kosan: Kosan, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onEditClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama Kosan: ${kosan.nama}", color = Color.Black)
            Text(text = "Alamat: ${kosan.alamat}", color = Color.Gray)
            Text(text = "Harga: ${kosan.harga}", color = Color.Gray)
            Text(text = "Fasilitas: ${kosan.fasilitas}", color = Color.Gray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onEditClick, modifier = Modifier.weight(1f)) {
                    Text("Edit")
                }

                Button(onClick = onDeleteClick, modifier = Modifier.weight(1f)) {
                    Text("Hapus")
                }
            }
        }
    }
}