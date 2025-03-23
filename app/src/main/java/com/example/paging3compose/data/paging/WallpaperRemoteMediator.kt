package com.example.paging3compose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3compose.data.db.WallpaperDatabase
import com.example.paging3compose.data.ktor.KtorEndPoints
import com.example.paging3compose.data.model.PhotoEntity

@OptIn(ExperimentalPagingApi::class)
class WallpaperRemoteMediator(
    private val query: String,
    private val api: KtorEndPoints,
    private val database: WallpaperDatabase
) : RemoteMediator<Int, PhotoEntity>() {

    private val dao = database.wallpaperDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (state.pages.size + 1)
                }
            }

            val response = api.getWallpaperData(query, page)
            val photoEntities = response.photos.map { photo ->
                PhotoEntity(
                    id = photo.id,
                    alt = photo.alt,
                    avg_color = photo.avg_color,
                    height = photo.height,
                    width = photo.width,
                    photographer = photo.photographer,
                    photographer_url = photo.photographer_url,
                    url = photo.url,
                    landscape = photo.src.landscape,
                    large = photo.src.large,
                    medium = photo.src.medium,
                    original = photo.src.original,
                    portrait = photo.src.portrait,
                    small = photo.src.small,
                    tiny = photo.src.tiny
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearWallpapers() // Remove old cache only on REFRESH
                }
                dao.insertWallpapers(photoEntities)
            }

            MediatorResult.Success(endOfPaginationReached = response.photos.isEmpty())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
