package com.example.paging3compose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapers")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val alt: String,
    val avg_color: String,
    val height: Int,
    val width: Int,
    val photographer: String,
    val photographer_url: String,
    val url: String,
    val landscape: String,
    val large: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String,
    val page: Int,
)
