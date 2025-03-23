package com.example.paging3compose.data.ktor

import com.example.paging3compose.data.model.ImageDataModel
import com.example.paging3compose.util.Const
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.delay

object KtorEndPoints {
    private var lastRequestTime = 0L
    private const val REQUEST_DELAY = 1000L // 1 second delay between requests

    suspend fun getWallpaperData(query: String, page: Int): ImageDataModel {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastRequest = currentTime - lastRequestTime

        if (timeSinceLastRequest < REQUEST_DELAY) {
            delay(REQUEST_DELAY - timeSinceLastRequest)
        }

        lastRequestTime = System.currentTimeMillis() // Update last request time

        return KtorClient.httpClient.get {
            url("${Const.BASE_URL}v1/search")
            parameter("query", query)
            parameter("page", page)
            header("Authorization", "${Const.API_KEY}")
        }
    }
}
