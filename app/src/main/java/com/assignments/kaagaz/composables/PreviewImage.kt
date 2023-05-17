package com.assignments.kaagaz.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun PreviewImage(modifier: Modifier = Modifier, url: String) {
    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            Uri.fromFile(
                File(url)
            )
        ),
        contentDescription = url
    )
}