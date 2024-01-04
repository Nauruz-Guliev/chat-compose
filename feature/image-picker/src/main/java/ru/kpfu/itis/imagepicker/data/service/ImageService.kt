package ru.kpfu.itis.imagepicker.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import ru.kpfu.itis.imagepicker.data.model.ImageResponse
import javax.inject.Inject

class ImageService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getImageResponse(query: String): ImageResponse =
        client.get {
            url {
                path("search/photos")
                parameter("query", query)
            }
        }.body()
}
