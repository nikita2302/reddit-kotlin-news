package com.loblaw.challenge.model

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("data")
    val data: Data,
    @SerializedName("kind")
    val kind: String
)