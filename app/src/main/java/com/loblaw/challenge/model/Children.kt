package com.loblaw.challenge.model

import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("data")
    val childData: ChildData,
    @SerializedName("kind")
    val kind: String
)