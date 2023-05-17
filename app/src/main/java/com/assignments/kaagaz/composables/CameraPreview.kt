package com.assignments.kaagaz.composables

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.assignments.kaagaz.utils.Utils.getProcessCameraProvider

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    imageCapture: ImageCapture,
    lensState: Int = CameraSelector.LENS_FACING_BACK
) {

    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preView = Preview.Builder().build()
    val preViewView = remember {
        PreviewView(localContext)
    }

    val cameraSelector = remember {
        CameraSelector.Builder().requireLensFacing(lensState).build()
    }

    LaunchedEffect(key1 = lensState, block = {
        val cameraProvider = localContext.getProcessCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preView, imageCapture)
        preView.setSurfaceProvider(preViewView.surfaceProvider)
    })


    AndroidView(
        modifier = modifier,
        factory = { preViewView }
    )
}
