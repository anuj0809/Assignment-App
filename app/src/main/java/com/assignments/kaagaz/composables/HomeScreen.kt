package com.assignments.kaagaz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assignments.kaagaz.models.Album

@Composable
fun HomeScreen(
    albums: List<Album>,
    onAddClick: () -> Unit,
    onAlbumClicked: (albumId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.BottomEnd
    ) {

        AlbumsList(modifier = Modifier.fillMaxSize(), albums = albums, onAlbumClicked)
        AddIcon(modifier = Modifier.padding(end = 16.dp, bottom = 32.dp)) {
            onAddClick()
        }
    }
}