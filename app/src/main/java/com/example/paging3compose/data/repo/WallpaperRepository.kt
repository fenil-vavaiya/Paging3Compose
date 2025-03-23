package com.example.paging3compose.data.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.paging3compose.data.dao.WallpaperDao
import com.example.paging3compose.data.db.WallpaperDatabase
import com.example.paging3compose.data.ktor.KtorEndPoints
import com.example.paging3compose.data.model.PhotoEntity
import com.example.paging3compose.data.paging.WallpaperRemoteMediator
import com.example.paging3compose.util.Const.TAG
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpaperRepository @Inject constructor(
    private val api: KtorEndPoints,
    private val database: WallpaperDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getWallpapers(query: String): Flow<PagingData<PhotoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 40,
                enablePlaceholders = false
            ),
            remoteMediator = WallpaperRemoteMediator(query, api, database),
            pagingSourceFactory = { database.wallpaperDao().getWallpapers() }
        ).flow
    }
}
