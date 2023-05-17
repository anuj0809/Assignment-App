package com.assignments.kaagaz

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignments.kaagaz.database.AppDao
import com.assignments.kaagaz.database.AppDatabase
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.PhotoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
class AppDaoTest {
    private lateinit var appDao: AppDao
    private lateinit var testDb: AppDatabase


    @Before
    fun createDbInstance() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDb = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        appDao = testDb.getAppDao()
    }

    @Test
    fun test_get_album_entities_expected_size_3() {
        runTest {
            appDao.insertAlbum(AlbumEntity("Album test 1"))
            appDao.insertAlbum(AlbumEntity("Album test 2"))
            appDao.insertAlbum(AlbumEntity("Album test 3"))
            val albumEntities = appDao.getAlbumsEntity()

            Assert.assertEquals(3, albumEntities.size)
            Assert.assertEquals("Album test 1", albumEntities[0].name)
        }
    }

    @Test
    fun test_get_photos_entities_expected_size_3() {
        runTest {
            val timeStamp = System.currentTimeMillis()
            val timeStamp2 = System.currentTimeMillis() + 20
            val timeStamp3 = System.currentTimeMillis() + 30
            val albumName = "Album test 1"


            appDao.insertAlbum(AlbumEntity(albumName))

            val albumId = appDao.getAlbumId(albumName)

            appDao.insertPhotos(
                listOf(
                    PhotoEntity(
                        albumId,
                        "Photo test 1",
                        timeStamp
                    ),
                    PhotoEntity(
                        albumId,
                        "Photo test 2",
                        timeStamp2
                    ),
                    PhotoEntity(
                        albumId,
                        "Photo test 3",
                        timeStamp3
                    )
                )
            )
            val photosEntity = appDao.getPhotosEntity(albumId)

            Assert.assertEquals(3, photosEntity.size)

            Assert.assertEquals("Photo test 1", photosEntity[0].name)

            Assert.assertEquals(timeStamp2, photosEntity[1].timeStamp)

            Assert.assertEquals(albumId, photosEntity[2].albumId)
        }
    }

    @Test
    fun testAlbumIdAndName() {
        val albumEntity = AlbumEntity("Album Test 1")
        runTest {
            appDao.insertAlbum(albumEntity)
            val albumId = appDao.getAlbumId(albumEntity.name)
            Assert.assertEquals(albumEntity.name, appDao.getAlbumName(albumId))
        }
    }


    @After
    fun closeDbInstance() {
        testDb.close()
    }
}