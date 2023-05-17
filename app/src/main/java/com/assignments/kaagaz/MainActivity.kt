package com.assignments.kaagaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.assignments.kaagaz.composables.AppNavHost
import com.assignments.kaagaz.composables.CustomSnackBar
import com.assignments.kaagaz.models.AppScreens
import com.assignments.kaagaz.ui.theme.KaagazTheme
import com.assignments.kaagaz.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    private val cameraExecutor by lazy { Executors.newSingleThreadExecutor() }

    companion object {
        const val TAG = "Kaagaz"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaagazTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val coroutineScope = rememberCoroutineScope()

                    val snackBarHost = remember { SnackbarHostState() }

                    fun launchSnackBar(msg: String) {
                        coroutineScope.launch {
                            snackBarHost.showSnackbar(msg)
                        }
                    }


                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {

                        AppNavHost(
                            navController = navController,
                            albums = appViewModel.albums.collectAsState(),
                            photos = appViewModel.photos.collectAsState(),
                            loadPhotos = { albumId ->
                                appViewModel.loadPhotos(albumId)
                            },
                            cameraExecutor = cameraExecutor,
                            onPhotosClicked = {
                                appViewModel.photosClicked(it)
                            },
                            getPreviewPhotos = {
                                appViewModel.getPreviewPhotos()
                            },
                            savePhotos = {
                                appViewModel.savePhotos {
                                    navController.popBackStack(AppScreens.HOME_SCREEN.key, false)
                                }
                            }) { msg ->
                            launchSnackBar(msg)
                        }

                        CustomSnackBar(hostState = snackBarHost)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        appViewModel.loadAlbums()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}