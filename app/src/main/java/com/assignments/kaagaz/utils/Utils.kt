package com.assignments.kaagaz.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.assignments.kaagaz.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Utils {
    private const val DATE_TIME_FORMATTER = "dd-MM-yyyy, HH:mm:ss"

    val REQUIRED_PERMISSIONS =
        mutableListOf(Manifest.permission.CAMERA).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()


    fun getFormattedText(timeStampInMillis: Long): String {
        val date = Date(timeStampInMillis)
        val format = SimpleDateFormat(DATE_TIME_FORMATTER, Locale.getDefault())
        return format.format(date)
    }

    suspend fun Context.getProcessCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this@getProcessCameraProvider).also {
                val res = it.get()
                it.addListener(
                    {
                        continuation.resume(res)
                    },
                    ContextCompat.getMainExecutor(this@getProcessCameraProvider)
                )
            }
        }

    @Composable
    fun CheckForPermissions(
        permissions: Array<String>,
        onPermissionRequestUpdate: (Boolean) -> Unit
    ) {
        val context = LocalContext.current

        val permissionLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { grantedMap ->
                onPermissionRequestUpdate(grantedMap.any { it.value.not() }.not())
            }

        LaunchedEffect(key1 = permissions, block = {
            val allPermissionsGranted = permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }

            if (allPermissionsGranted) {
                onPermissionRequestUpdate(true)
            } else {
                permissionLauncher.launch(permissions)
            }
        })
    }
}