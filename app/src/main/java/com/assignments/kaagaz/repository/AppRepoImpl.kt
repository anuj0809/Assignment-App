package com.assignments.kaagaz.repository

import com.assignments.kaagaz.database.AppDatabase
import com.assignments.kaagaz.di.IoDispatchers
import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.models.PhotoEntity
import com.assignments.kaagaz.utils.FileManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val fileManager: FileManager,
    @IoDispatchers private val ioDispatcher: CoroutineDispatcher
) : AppRepo {

    private val appDao by lazy { appDatabase.getAppDao() }

    override suspend fun getAlbumsEntities(): List<AlbumEntity> = withContext(ioDispatcher) {
        appDao.getAlbumsEntity()
    }

    override suspend fun getPhotosEntities(albumId: Int): List<PhotoEntity> =
        withContext(ioDispatcher) {
            appDao.getPhotosEntity(albumId)
        }

    override suspend fun savePhotos(
        photos: List<ByteArrayOutputStream>,
        onPhotosSaved: suspend () -> Unit
    ) = withContext(ioDispatcher) {
        fileManager.savePhotos(photos) { photosEntities, albumName ->
            val albumId = appDao.getAlbumId(albumName)
            appDao.insertPhotos(photosEntities.map { photoHelperClass ->
                PhotoEntity(albumId, photoHelperClass.name, photoHelperClass.timeStamp)
            })
            appDao.insertAlbum(AlbumEntity(name = albumName))
            onPhotosSaved()
        }
    }

    override suspend fun getAlbums(albumEntities: List<AlbumEntity>): List<Album> =
        withContext(ioDispatcher) {
            fileManager.getAlbums(albumEntities)
        }

    override suspend fun getPhotos(albumId: Int, photos: List<PhotoEntity>): List<Photo> {
        return withContext(ioDispatcher) {
            val albumName = appDao.getAlbumName(albumId)
            val postEntities = appDao.getPhotosEntity(albumId)
            fileManager.getPhotos(
                albumId,
                albumName,
                postEntities
            )
        }
    }
}