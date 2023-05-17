package com.assignments.kaagaz.composables

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.assignments.kaagaz.models.Album
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumsList(
    modifier: Modifier = Modifier,
    albums: List<Album>,
    onAlbumClicked: (albumId: Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        content = {
            items(albums) { album ->
                AlbumView(album = album) { id ->
                    onAlbumClicked(id)
                }
            }
        })
}


@Composable
fun AlbumView(
    album: Album,
    onAlbumClicked: (albumId: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onAlbumClicked(album.id) },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PreviewImage(modifier = Modifier.size(80.dp), url = album.thumbUrl)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = album.name, color = Color.Black, textAlign = TextAlign.Center)
        }
    }
}