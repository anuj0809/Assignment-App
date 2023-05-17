package com.assignments.kaagaz.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.models.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("Select * from AlbumEntity")
    fun getAlbumsEntity(): List<AlbumEntity>

    @Query("Select * from PhotoEntity where albumId = :albumId")
    fun getPhotosEntity(albumId: Int): List<PhotoEntity>

    @Insert
    suspend fun insertAlbum(album: AlbumEntity)

    @Insert
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("Select id from AlbumEntity where name = :name")
    fun getAlbumId(name: String): Int

    @Query("Select name from AlbumEntity where id = :id")
    fun getAlbumName(id: Int): String
}