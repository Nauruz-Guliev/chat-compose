package ru.kpfu.itis.image_picker.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
) {
    @Serializable
    data class Result(
        @SerialName("alt_description")
        val altDescription: String,
        @SerialName("blur_hash")
        val blurHash: String,
        @SerialName("breadcrumbs")
        val breadcrumbs: List<String>,
        @SerialName("color")
        val color: String,
        @SerialName("created_at")
        val createdAt: String,
        @SerialName("current_user_collections")
        val currentUserCollections: List<String>,
        @SerialName("description")
        val description: String?,
        @SerialName("height")
        val height: Int,
        @SerialName("id")
        val id: String,
        @SerialName("liked_by_user")
        val likedByUser: Boolean,
        @SerialName("likes")
        val likes: Int,
        @SerialName("links")
        val links: Links,
        @SerialName("promoted_at")
        val promotedAt: String?,
        @SerialName("slug")
        val slug: String,
        @SerialName("sponsorship")
        val sponsorship: String?,
        @SerialName("tags")
        val tags: List<Tag>,
        @SerialName("topic_submissions")
        val topicSubmissions: TopicSubmissions?,
        @SerialName("updated_at")
        val updatedAt: String,
        @SerialName("urls")
        val urls: Urls,
        @SerialName("user")
        val user: User,
        @SerialName("width")
        val width: Int
    ) {
        @Serializable
        data class Links(
            @SerialName("download")
            val download: String,
            @SerialName("download_location")
            val downloadLocation: String,
            @SerialName("html")
            val html: String,
            @SerialName("self")
            val self: String
        )

        @Serializable
        data class Tag(
            @SerialName("title")
            val title: String,
            @SerialName("type")
            val type: String
        )

        @Serializable
        class TopicSubmissions

        @Serializable
        data class Urls(
            @SerialName("full")
            val full: String,
            @SerialName("raw")
            val raw: String,
            @SerialName("regular")
            val regular: String,
            @SerialName("small")
            val small: String,
            @SerialName("small_s3")
            val smallS3: String,
            @SerialName("thumb")
            val thumb: String
        )

        @Serializable
        data class User(
            @SerialName("accepted_tos")
            val acceptedTos: Boolean,
            @SerialName("bio")
            val bio: String?,
            @SerialName("first_name")
            val firstName: String,
            @SerialName("for_hire")
            val forHire: Boolean,
            @SerialName("id")
            val id: String,
            @SerialName("instagram_username")
            val instagramUsername: String,
            @SerialName("last_name")
            val lastName: String,
            @SerialName("links")
            val links: Links,
            @SerialName("location")
            val location: String?,
            @SerialName("name")
            val name: String,
            @SerialName("portfolio_url")
            val portfolioUrl: String?,
            @SerialName("profile_image")
            val profileImage: ProfileImage,
            @SerialName("social")
            val social: Social,
            @SerialName("total_collections")
            val totalCollections: Int,
            @SerialName("total_likes")
            val totalLikes: Int,
            @SerialName("total_photos")
            val totalPhotos: Int,
            @SerialName("total_promoted_photos")
            val totalPromotedPhotos: Int,
            @SerialName("twitter_username")
            val twitterUsername: String?,
            @SerialName("updated_at")
            val updatedAt: String,
            @SerialName("username")
            val username: String
        ) {
            @Serializable
            data class Links(
                @SerialName("followers")
                val followers: String,
                @SerialName("following")
                val following: String,
                @SerialName("html")
                val html: String,
                @SerialName("likes")
                val likes: String,
                @SerialName("photos")
                val photos: String,
                @SerialName("portfolio")
                val portfolio: String,
                @SerialName("self")
                val self: String
            )

            @Serializable
            data class ProfileImage(
                @SerialName("large")
                val large: String,
                @SerialName("medium")
                val medium: String,
                @SerialName("small")
                val small: String
            )

            @Serializable
            data class Social(
                @SerialName("instagram_username")
                val instagramUsername: String,
                @SerialName("paypal_email")
                val paypalEmail: String?,
                @SerialName("portfolio_url")
                val portfolioUrl: String?,
                @SerialName("twitter_username")
                val twitterUsername: String?
            )
        }
    }
}