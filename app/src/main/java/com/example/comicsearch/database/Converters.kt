package com.example.comicsearch.database

import androidx.room.TypeConverter
import com.example.comicsearch.api.ImageUrl
import com.squareup.moshi.JsonAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject

class Converters() : KoinComponent {
    val adapter by inject<JsonAdapter<ImageUrl>>()
    @TypeConverter
    fun fromString(value: String): ImageUrl? {

        return adapter.fromJson(value)
    }

    @TypeConverter
    fun ImageUrlString(value: ImageUrl): String {
        return adapter.toJson(value)
    }
}