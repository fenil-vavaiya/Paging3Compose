package com.example.paging3compose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3compose.data.ktor.KtorEndPoints
import com.example.paging3compose.data.model.ImageDataModel

class MyPagingSource(private val query: String) : PagingSource<Int, ImageDataModel.Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDataModel.Photo> {
        return try {
            val page = params.key ?: 1
            val response = KtorEndPoints.getWallpaperData(query, page)

            LoadResult.Page(
                data = response.photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, ImageDataModel.Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
