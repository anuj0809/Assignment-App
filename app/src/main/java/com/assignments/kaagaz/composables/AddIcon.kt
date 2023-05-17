package com.assignments.kaagaz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignments.kaagaz.R

@Composable
fun AddIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Icon(
        modifier = modifier
            .clickable {
                onClick()
            }
            .clip(CircleShape)
            .background(Color.DarkGray)
            .padding(16.dp),
        imageVector = Icons.Filled.Add,
        tint = Color.White,
        contentDescription = stringResource(R.string.add_icon)
    )
}


@Preview
@Composable
fun AddIconPreview() {
    AddIcon {}
}
