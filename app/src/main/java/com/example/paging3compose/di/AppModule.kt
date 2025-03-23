package com.example.paging3compose.di

import android.app.Application
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.paging3compose.data.db.WallpaperDatabase
import com.example.paging3compose.data.ktor.KtorEndPoints
import com.example.paging3compose.data.model.PhotoEntity
import com.example.paging3compose.data.paging.WallpaperRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@OptIn(ExperimentalPagingApi::class)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideImagePager(
        imageDb: WallpaperDatabase, imageApi: KtorEndPoints
    ): Pager<Int, PhotoEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 20,
                enablePlaceholders = false
            ), remoteMediator = WallpaperRemoteMediator(
                database = imageDb, api = imageApi
            ), pagingSourceFactory = {
                imageDb.wallpaperDao().pagingSource()
            })
    }

    @Provides
    fun provideWallpaperDatabase(application: Application): WallpaperDatabase {
        return Room.databaseBuilder(
            application, WallpaperDatabase::class.java, "wallpaper_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }

        return HttpClient(Android) {
            install(HttpTimeout) {
                socketTimeoutMillis = 30000
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorClient", "KtorClient: $message")
                    }
                }
                level = LogLevel.BODY
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideKtorEndPoints(): KtorEndPoints {
        return KtorEndPoints
    }
}
