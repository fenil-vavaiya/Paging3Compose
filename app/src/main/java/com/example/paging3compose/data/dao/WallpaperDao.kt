package com.example.paging3compose.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3compose.data.model.PhotoEntity

@Dao
interface WallpaperDao {
    @Query("SELECT * FROM wallpapers")
    fun getWallpapers(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpapers(wallpapers: List<PhotoEntity>)

    @Query("DELETE FROM wallpapers")
    suspend fun clearWallpapers()
}


