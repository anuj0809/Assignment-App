package com.assignments.kaagaz.repository

import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.models.PhotoEntity
import java.io.ByteArrayOutputStream

interface AppRepo {
    suspend fun getAlbumsEntities(): List<AlbumEntity>
    suspend fun getPhotosEntities(albumId: Int): List<PhotoEntity>
    suspend fun savePhotos(photos: List<ByteArrayOutputStream>, onPhotosSaved: suspend () -> Unit)
    suspend fun getAlbums(albumEntities: List<AlbumEntity>): List<Album>
    suspend fun getPhotos(albumId: Int, photos: List<PhotoEntity>): List<Photo>
}