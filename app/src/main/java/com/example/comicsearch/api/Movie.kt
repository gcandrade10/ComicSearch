package com.example.comicsearch.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Int = Int.MAX_VALUE,
    val name: String = "",
    val description: String? = "",
    val image: ImageUrl = ImageUrl(thumb_url = "", medium_url = "")
)

class ImageUrl(
    var thumb_url: String,
    var medium_url: String
)