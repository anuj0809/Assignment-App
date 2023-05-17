package com.assignments.kaagaz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignments.kaagaz.models.Album
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.Photo
import com.assignments.kaagaz.models.PhotoEntity


@Database(entities = [AlbumEntity::class, PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "AppDatabase"
    }

    abstract fun getAppDao(): AppDao
}