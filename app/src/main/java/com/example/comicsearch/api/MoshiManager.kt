package com.example.comicsearch.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object MoshiManager {

    private fun init(): JsonAdapter<ImageUrl> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(ImageUrl::class.java)
    }

    val moshi by lazy { init() }
}