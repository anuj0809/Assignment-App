package com.assignments.kaagaz.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun PermissionLauncher(permissions: Array<String>, onAllPermissionsGranted: () -> Unit) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { grantedMap ->
            grantedMap.values.forEach { result ->
                if (result.not()) {
                    return@rememberLauncherForActivityResult
                }
            }

            onAllPermissionsGranted()
        }
    )

    permissionLauncher.launch(permissions)
}