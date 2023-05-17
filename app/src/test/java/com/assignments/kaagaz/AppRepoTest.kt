package com.assignments.kaagaz

import com.assignments.kaagaz.database.AppDao
import com.assignments.kaagaz.database.AppDatabase
import com.assignments.kaagaz.models.AlbumEntity
import com.assignments.kaagaz.models.PhotoEntity
import com.assignments.kaagaz.repository.AppRepo
import com.assignments.kaagaz.repository.AppRepoImpl
import com.assignments.kaagaz.utils.FileManager
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AppRepoTest {

    @MockK
    private lateinit var appDatabase: AppDatabase

    @MockK
    private lateinit var fileManager: FileManager

    private lateinit var appRepo: AppRepo

    @MockK
    private lateinit var appDao: AppDao


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        appRepo = AppRepoImpl(appDatabase, fileManager, Dispatchers.IO)
        every { appDatabase.getAppDao() } returns appDao
    }

    @Test
    fun `test getAlbumsEntities`() {
        every { appDao.getAlbumsEntity() } returns listOf(
            AlbumEntity("Album test 1"),
            AlbumEntity("Album test 2"),
            AlbumEntity("Album test 3")
        )

        runTest {
            val albumEntities = appRepo.getAlbumsEntities()
            Assert.assertEquals(3, albumEntities.size)

            Assert.assertEquals("Album test 1", albumEntities[0].name)
        }
    }

    @After
    fun clearUp() {
        clearAllMocks()
        unmockkAll()
    }

}