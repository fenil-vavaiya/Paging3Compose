package com.example.paging3compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3compose.data.dao.WallpaperDao
import com.example.paging3compose.data.model.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao
}
