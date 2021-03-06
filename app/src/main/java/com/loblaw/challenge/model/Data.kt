package com.loblaw.challenge.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("after")
    val after: String,
    @SerializedName("children")
    val children: List<Children>
)