package com.assignments.kaagaz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream

@Composable
fun PreviewPhotos(
    photos: List<ByteArrayOutputStream>,
    onBackClicked: () -> Unit,
    onProceedClick: () -> Unit
) {
    var currentSelectedPost by remember {
        mutableStateOf(0)
    }

    Column(Modifier.fillMaxSize()) {
        ImagePreview(modifier = Modifier.weight(1f), byteOutputStream = photos[currentSelectedPost])
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        if (currentSelectedPost == 0) {
                            onBackClicked()
                        } else {
                            currentSelectedPost--
                        }
                    },
                imageVector = Icons.Filled.NavigateBefore,
                contentDescription = "Previous",
                tint = Color.White
            )

            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        if (currentSelectedPost == photos.size - 1) {
                            onProceedClick()
                        } else {
                            currentSelectedPost++
                        }
                    },
                imageVector = Icons.Filled.NavigateNext,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}