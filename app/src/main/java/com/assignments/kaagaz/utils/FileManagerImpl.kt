package com.assignments.kaagaz.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.assignments.kaagaz.MainActivity
import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.models.PhotoEntity
import com.assignments.kaagaz.models.PhotoHelperClass
import com.assignments.kaagaz.utils.Utils.getFormattedText
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileManager {

    companion object {
        private const val PARENT_DIRECTORY = "Kaagaz"
        private const val ALBUM = "Album"
    }

    private fun createAlbum(albumName: String): File {
        val mediaDir = try {
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
                File(
                    it, "$PARENT_DIRECTORY/$albumName"
                ).apply { mkdirs() }
            }
        } catch (e: Throwable) {
            Log.e(MainActivity.TAG, e.message.toString())
            null
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    private fun getOutputDirectory(): File? {
        return try {
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
                File(it, PARENT_DIRECTORY)
            }
        } catch (e: Throwable) {
            Log.e(MainActivity.TAG, e.message.toString())
            null
        }
    }

    private fun getAlbumDirectory(albumName: String): File? {
        return try {
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
                File(it, "$PARENT_DIRECTORY/$albumName")
            }
        } catch (e: Throwable) {
            Log.e(MainActivity.TAG, e.message.toString())
            null
        }
    }

    override suspend fun savePhotos(
        photos: List<ByteArrayOutputStream>,
        onPhotosSaved: suspend (photos: List<PhotoHelperClass>, albumName: String) -> Unit
    ) {
        val albumName = "$ALBUM ${getFormattedText(System.currentTimeMillis())}"
        val photosEntities = mutableListOf<PhotoHelperClass>()
        val outputDirectory = createAlbum(albumName)
        photos.forEach { photo ->
            try {
                val timeStamp = System.currentTimeMillis()
                val photoEntity = PhotoHelperClass(
                    name = "Photo $timeStamp.jpg",
                    timeStamp = timeStamp
                )
                val file = File(
                    outputDirectory,
                    photoEntity.name
                )
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(photo.toByteArray())
                fileOutputStream.close()
            } catch (e: Exception) {
                Log.e(MainActivity.TAG, e.message.toString())
            }
        }

        onPhotosSaved(photosEntities, albumName)
    }

    override suspend fun getAlbums(albumEntities: List<AlbumEntity>): List<Album> {
        val albums: MutableList<Album> = mutableListOf()
        getOutputDirectory()?.let { outputDirectory ->
            if (outputDirectory.isDirectory) {
                outputDirectory.listFiles()?.forEachIndexed { pos, file ->
                    if (file.isDirectory) {
                        file.listFiles()?.get(0)?.let {
                            albums.add(
                                Album(
                                    thumbUrl = it.absolutePath,
                                    name = albumEntities.getOrNull(pos)?.name
                                        ?: file.nameWithoutExtension,
                                    id = albumEntities.getOrNull(pos)?.id ?: -1
                                )
                            )
                        }
                    }
                }
            }
        }
        return albums
    }

    override suspend fun getPhotos(
        albumId: Int,
        albumName: String,
        photoEntities: List<PhotoEntity>
    ): List<Photo> {
        val photos = mutableListOf<Photo>()
        getAlbumDirectory(albumName)?.let { outputDirectory ->
            if (outputDirectory.isDirectory) {
                outputDirectory.listFiles()?.forEachIndexed { pos, file ->
                    photos.add(
                        Photo(
                            albumId,
                            photoEntities.getOrNull(pos)?.name ?: file.nameWithoutExtension,
                            timeStamp = photoEntities.getOrNull(pos)?.timeStamp
                                ?: file.lastModified(),
                            url = file.absolutePath
                        )
                    )
                }
            }
        }
        return photos
    }
}