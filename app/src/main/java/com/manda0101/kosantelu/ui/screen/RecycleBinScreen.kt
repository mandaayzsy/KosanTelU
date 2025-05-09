package com.manda0101.kosantelu.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@Composable
fun RecycleBinScreen(viewModel: MainViewModel) {
    // Collecting the deleted kosans from the ViewModel's flow
    val deletedKosans = viewModel.allDeletedKosans.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Recycle Bin", style = MaterialTheme.typography.headlineLarge)

        // Display deleted kosans in LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(deletedKosans.value) { recycleBin ->
                RecycleBinItem(
                    recycleBin = recycleBin,
                    viewModel = viewModel,  // Pass the viewModel here
                    onRestoreClick = { viewModel.restoreKosan(recycleBin) },
                    onDeletePermanentlyClick = { viewModel.permanentlyDeleteKosan(recycleBin) }
                )
            }
        }
    }
}
