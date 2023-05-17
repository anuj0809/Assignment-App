package com.assignments.kaagaz.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ViewPhoto(photoUrl: String) {
    PreviewImage(modifier = Modifier.fillMaxSize(), url = photoUrl)
}