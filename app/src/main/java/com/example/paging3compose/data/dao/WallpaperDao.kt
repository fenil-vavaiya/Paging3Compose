package com.example.paging3compose.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.paging3compose.data.model.PhotoEntity
import com.example.paging3compose.data.model.RemoteKeys

@Dao
interface WallpaperDao {

    @Upsert
    suspend fun upsertAll(images: List<PhotoEntity>)

    @Query("SELECT * FROM wallpapers")
    fun pagingSource(): PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM wallpapers")
    suspend fun clearAll()

    @Query("SELECT * FROM remote_keys WHERE imageId = :id")
    suspend fun getRemoteKeys(id: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKeys>)

    @Query("SELECT * FROM wallpapers WHERE page = :page LIMIT 20")
    suspend fun getWallpapersForPage(page: Int): List<PhotoEntity>

}


