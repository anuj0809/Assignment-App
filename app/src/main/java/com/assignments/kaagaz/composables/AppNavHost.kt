package com.assignments.kaagaz.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AppScreens
import com.assignments.kaagaz.models.Photo
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor

@Composable
fun AppNavHost(
    navController: NavHostController,
    albums: State<List<Album>>,
    photos: State<List<Photo>>,
    loadPhotos: (albumId: Int) -> Unit,
    cameraExecutor: Executor,
    onPhotosClicked: (photos: List<ByteArrayOutputStream>) -> Unit,
    getPreviewPhotos: () -> List<ByteArrayOutputStream>,
    savePhotos: () -> Unit,
    launchSnackBar: (msg: String) -> Unit
) {

    NavHost(navController = navController, startDestination = AppScreens.HOME_SCREEN.key) {
        composable(AppScreens.HOME_SCREEN.key) {
            HomeScreen(albums = albums.value, onAddClick = {
                navController.navigate(AppScreens.ADD_PHOTOS.key)
            }) { albumId ->
                navController.navigate("${AppScreens.VIEW_PHOTOS.key}/$albumId")
            }
        }

        composable(
            "${AppScreens.VIEW_PHOTOS.key}/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("albumId")?.let { albumId ->
                loadPhotos(albumId)
                PhotosList(photos = photos.value, onPhotoClicked = { photoUrl ->
                    navController.navigate(
                        "${AppScreens.VIEW_PHOTO.key}/${
                            photoUrl.replace(
                                "/",
                                "|"
                            )
                        }"
                    )
                })
            }
        }


        composable(
            "${AppScreens.VIEW_PHOTO.key}/{photoUrl}"
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("photoUrl")?.let { photoUrl ->
                ViewPhoto(photoUrl = photoUrl.replace('|', '/'))
            }
        }

        composable(AppScreens.ADD_PHOTOS.key) {
            AddPhotos(cameraExecutor = cameraExecutor, permissionsNotGranted = {
                launchSnackBar("All permissions should be granted")
                navController.popBackStack()
            }) { photos ->
                if (photos.isNotEmpty()) {
                    onPhotosClicked(photos)
                    navController.navigate(AppScreens.PREVIEW_PHOTOS.key)
                } else {
                    launchSnackBar("Please capture at least one photo")
                }
            }
        }

        composable(AppScreens.PREVIEW_PHOTOS.key) {
            PreviewPhotos(photos = getPreviewPhotos(), onBackClicked = {
                navController.popBackStack()
            }) {
                savePhotos()
            }
        }
    }
}