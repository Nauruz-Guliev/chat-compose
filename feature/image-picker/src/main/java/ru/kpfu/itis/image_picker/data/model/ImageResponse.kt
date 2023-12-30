package ru.kpfu.itis.image_picker.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("results")
    val results: List<Result> = listOf(),
    @SerialName("total")
    val total: Int = 0,
    @SerialName("total_pages")
    val totalPages: Int = 0
) {
    @Serializable
    data class Result(
        @SerialName("urls")
        val urls: Urls = Urls(),
    )

    @Serializable
    data class Urls(
        @SerialName("full")
        val full: String = "",
        @SerialName("raw")
        val raw: String = "",
        @SerialName("regular")
        val regular: String = "",
        @SerialName("small")
        val small: String = "",
        @SerialName("small_s3")
        val smallS3: String = "",
        @SerialName("thumb")
        val thumb: String = ""
    )
}