package com.example.paging3compose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val imageId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)