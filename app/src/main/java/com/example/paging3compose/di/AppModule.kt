package com.example.paging3compose.di

import android.content.Context
import androidx.room.Room
import com.example.paging3compose.data.dao.WallpaperDao
import com.example.paging3compose.data.db.WallpaperDatabase
import com.example.paging3compose.data.ktor.KtorEndPoints
import com.example.paging3compose.data.repo.WallpaperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WallpaperDatabase {
        return Room.databaseBuilder(
            context,
            WallpaperDatabase::class.java,
            "wallpaper_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDao(database: WallpaperDatabase): WallpaperDao {
        return database.wallpaperDao()
    }

    @Provides
    fun provideRepository(database: WallpaperDatabase): WallpaperRepository {
        return WallpaperRepository(KtorEndPoints, database)
    }
}
