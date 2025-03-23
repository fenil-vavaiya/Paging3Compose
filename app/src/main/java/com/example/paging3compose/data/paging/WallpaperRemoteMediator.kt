package com.example.paging3compose.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.paging3compose.data.db.WallpaperDatabase
import com.example.paging3compose.data.ktor.KtorEndPoints
import com.example.paging3compose.data.model.PhotoEntity
import com.example.paging3compose.data.model.RemoteKeys
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.math.pow

@OptIn(ExperimentalPagingApi::class)
class WallpaperRemoteMediator(
    private val database: WallpaperDatabase,
    private val api: KtorEndPoints
) : RemoteMediator<Int, PhotoEntity>() {

    private val dao = database.wallpaperDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PhotoEntity>): MediatorResult {
        var retryCount = 0
        val maxRetries = 3 // Limit retries to prevent infinite loops


        while (retryCount < maxRetries) {
            try {
                val page = when (loadType) {
                    LoadType.REFRESH -> 1
                    LoadType.PREPEND -> {
                        val firstItem = state.firstItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = false)
                        dao.getRemoteKeys(firstItem.id)?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = false)
                    }
                    LoadType.APPEND -> {
                        val lastItem = state.lastItemOrNull()
                            ?: return MediatorResult.Success(endOfPaginationReached = false) // Keep trying

                        val remoteKeys = dao.getRemoteKeys(lastItem.id)
                        Log.d("WallpaperRemoteMediator", "RemoteKeys for ${lastItem.id}: Prev=${remoteKeys?.prevKey}, Next=${remoteKeys?.nextKey}")
                        val nextPage = remoteKeys?.nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = true) // Stop if no next key


                        nextPage
                    }
                }


                val response = api.getWallpaperData("people", page)


                val endOfPaginationReached = response.photos.isEmpty() || response.photos.size < state.config.pageSize

                Log.d("WallpaperRemoteMediator", "LoadType: ${loadType.name}, Page: $page, EndReached: $endOfPaginationReached")

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        dao.clearAll()
                        dao.clearRemoteKeys()
                    }

                    val keys = response.photos.map { dto ->
                        RemoteKeys(
                            imageId = dto.id,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = /*if (endOfPaginationReached) null else*/ page + 1
                        )
                    }
                    dao.insertAll(keys)

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
                            tiny = photo.src.tiny,
                            page = response.page
                        )
                    }

                    dao.upsertAll(photoEntities)
                }

                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

            } catch (e: IOException) {
                return MediatorResult.Error(e)
            } catch (e: HttpException) {
                if (!e.response.isSuccessful) {
                    retryCount++
                    val retryDelay = (2.0.pow(retryCount) * 1000).toLong() // Exponential backoff
                    delay(retryDelay)
                    continue // Retry loading instead of failing
                } else {
                    return MediatorResult.Error(e)
                }
            }
        }
        return MediatorResult.Error(Exception("Max retries reached"))
    }

}
