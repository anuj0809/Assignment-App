package com.assignments.kaagaz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignments.kaagaz.di.MainDispatchers
import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.repository.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepo: AppRepo,
    @MainDispatchers private val mainDispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _albums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())
    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    private var previewPhotos: List<ByteArrayOutputStream>? = null

    val albums: StateFlow<List<Album>> = _albums
    val photos: StateFlow<List<Photo>> = _photos

    fun loadAlbums() {
        viewModelScope.launch {
            val albumEntities = appRepo.getAlbumsEntities()
            val albums = appRepo.getAlbums(albumEntities)
            _albums.emit(albums)
        }
    }

    fun loadPhotos(albumId: Int) {
        viewModelScope.launch {
            val photosEntities = appRepo.getPhotosEntities(albumId)
            val photos = appRepo.getPhotos(albumId, photosEntities)
            _photos.emit(photos)
        }
    }

    fun photosClicked(photos: List<ByteArrayOutputStream>) {
        previewPhotos = photos
    }

    fun getPreviewPhotos(): List<ByteArrayOutputStream> = previewPhotos.orEmpty()

    fun savePhotos(onPhotosSaved: () -> Unit) {
        previewPhotos?.let { photos ->
            viewModelScope.launch {
                appRepo.savePhotos(photos) {
                    loadAlbums()
                    withContext(mainDispatchers) {
                        onPhotosSaved()
                    }
                }
            }
        }
    }
}