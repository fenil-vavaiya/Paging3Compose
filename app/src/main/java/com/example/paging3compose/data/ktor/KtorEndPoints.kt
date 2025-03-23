package com.example.paging3compose.data.ktor

import com.example.paging3compose.data.model.ImageDataModel
import com.example.paging3compose.util.Const
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

object KtorEndPoints {
    suspend fun getWallpaperData(query: String, page: Int): ImageDataModel =
        KtorClient.httpClient.get {
            url("${Const.BASE_URL}v1/search")
            parameter("query", query)
            parameter("page", page)
            header("Authorization", "${Const.API_KEY}")
        }
}