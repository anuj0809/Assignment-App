package com.assignments.kaagaz.utils

import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.models.PhotoEntity
import com.assignments.kaagaz.models.PhotoHelperClass
import java.io.ByteArrayOutputStream

interface FileManager {
    suspend fun savePhotos(
        photos: List<ByteArrayOutputStream>,
        onPhotosSaved: suspend (photos: List<PhotoHelperClass>, albumName: String) -> Unit
    )

    suspend fun getPhotos(
        albumId: Int,
        albumName: String,
        photoEntities: List<PhotoEntity>
    ): List<Photo>

    suspend fun getAlbums(albumEntities: List<AlbumEntity>): List<Album>
}