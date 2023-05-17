package com.assignments.kaagaz.composables

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.utils.Utils
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosList(
    photos: List<Photo>,
    onPhotoClicked: (photoUrl: String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        columns = StaggeredGridCells.Fixed(2),
        content = {
            items(photos) { photo ->
                PhotoView(photo = photo) { url ->
                    onPhotoClicked(url)
                }
            }
        })
}


@Composable
fun PhotoView(
    photo: Photo,
    onPhotoClicked: (photoUrl: String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onPhotoClicked(photo.url) },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PreviewImage(modifier = Modifier.size(80.dp), url = photo.url)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = photo.name, color = Color.Black, fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = Utils.getFormattedText(photo.timeStamp),
                color = Color.DarkGray,
                fontSize = 12.sp
            )
        }
    }
}