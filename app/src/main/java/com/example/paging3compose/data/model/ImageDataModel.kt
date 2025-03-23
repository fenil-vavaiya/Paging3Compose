package com.example.paging3compose.data.model


import kotlinx.serialization.Serializable

@Serializable
data class ImageDataModel(
    val next_page: String = "",
    val page: Int = 0,
    val per_page: Int = 0,
    val photos: List<Photo> = listOf(),
    val prev_page: String = "",
    val total_results: Int = 0
) {
    @Serializable
    data class Photo(
        val alt: String = "",
        val avg_color: String = "",
        val height: Int = 0,
        val id: Int = 0,
        val liked: Boolean = false,
        val photographer: String = "",
        val photographer_id: Int = 0,
        val photographer_url: String = "",
        val src: Src = Src(),
        val url: String = "",
        val width: Int = 0
    ) {
        @Serializable
        data class Src(
            val landscape: String = "",
            val large: String = "",
            val large2x: String = "",
            val medium: String = "",
            val original: String = "",
            val portrait: String = "",
            val small: String = "",
            val tiny: String = ""
        )
    }
}