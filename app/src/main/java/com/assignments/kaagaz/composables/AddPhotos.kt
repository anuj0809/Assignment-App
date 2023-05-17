package com.assignments.kaagaz.composables

import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.assignments.kaagaz.MainActivity
import com.assignments.kaagaz.utils.Utils
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor

@Composable
fun AddPhotos(
    cameraExecutor: Executor,
    permissionsNotGranted: () -> Unit,
    onPhotosClicked: (photos: List<ByteArrayOutputStream>) -> Unit
) {

    Utils.CheckForPermissions(
        permissions = Utils.REQUIRED_PERMISSIONS,
        onPermissionRequestUpdate = { granted ->
            if (granted.not()) {
                permissionsNotGranted()
            }
        })

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }


    val capturedPhotos = remember {
        mutableStateListOf<ByteArrayOutputStream>()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
            CameraPreview(modifier = Modifier.fillMaxSize(), imageCapture)
            CapturedImages(
                modifier = Modifier
                    .fillMaxWidth(),
                capturedImages = capturedPhotos
            )
        }

        TakePhotoUi({
            takePhoto(imageCapture, cameraExecutor) { image ->
                capturedPhotos.add(image)
            }
        }) {
            onPhotosClicked(capturedPhotos)
        }
    }
}

@Composable
fun CapturedImages(
    modifier: Modifier = Modifier,
    capturedImages: List<ByteArrayOutputStream>
) {
    LazyRow(modifier = modifier, contentPadding = PaddingValues(5.dp)) {
        items(capturedImages) { image ->
            ImagePreview(
                Modifier.size(80.dp), byteOutputStream = image
            )
        }
    }
}

@Composable
fun ImagePreview(
    modifier: Modifier = Modifier,
    byteOutputStream: ByteArrayOutputStream
) {
    val imagePainter = rememberAsyncImagePainter(byteOutputStream.toByteArray())

    Image(
        modifier = modifier,
        painter = imagePainter,
        contentDescription = "Previewed Image"
    )
}

@Composable
fun TakePhotoUi(captureImage: () -> Unit, onPhotosCaptured: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable {
                captureImage()
            })

        Icon(
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterEnd)
                .clickable {
                    onPhotosCaptured()
                },
            imageVector = Icons.Filled.NavigateNext,
            contentDescription = "Next",
            tint = Color.White
        )
    }
}


private fun takePhoto(
    imageCapture: ImageCapture,
    executor: Executor,
    onImageCaptured: (byteOutputStream: ByteArrayOutputStream) -> Unit
) {

    val byteOutputStream = ByteArrayOutputStream()
    val outputOptions = ImageCapture.OutputFileOptions.Builder(byteOutputStream).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e(MainActivity.TAG, exception.message.toString())
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            onImageCaptured(byteOutputStream)
        }
    })
}