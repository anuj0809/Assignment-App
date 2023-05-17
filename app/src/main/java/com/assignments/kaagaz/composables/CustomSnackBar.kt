package com.assignments.kaagaz.composables

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun CustomSnackBar(hostState: SnackbarHostState) {
    SnackbarHost(
        hostState = hostState
    ) {
        Snackbar(
            shape = RectangleShape,
            containerColor = Color.Black.copy(alpha = 0.6f),
        ) {
            Text(it.visuals.message, color = Color.White)
        }
    }
}