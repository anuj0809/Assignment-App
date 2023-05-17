package com.assignments.kaagaz.di

import android.content.Context
import androidx.room.Room
import com.assignments.kaagaz.database.AppDatabase
import com.assignments.kaagaz.repository.AppRepo
import com.assignments.kaagaz.repository.AppRepoImpl
import com.assignments.kaagaz.utils.FileManager
import com.assignments.kaagaz.utils.FileManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvider {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    @IoDispatchers
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatchers
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DefaultDispatchers
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinder() {
    @Binds
    @Singleton
    abstract fun bindsAppRepository(appRepoImpl: AppRepoImpl): AppRepo

    @Binds
    @Singleton
    abstract fun bindsFileManager(fileManagerImpl: FileManagerImpl): FileManager
}
