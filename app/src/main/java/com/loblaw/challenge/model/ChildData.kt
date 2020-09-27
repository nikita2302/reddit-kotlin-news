package com.loblaw.challenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChildData(
    @SerializedName("author")
    val author: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("secure_media")
    val secureMedia: SecureMedia?,
    @SerializedName("selftext")
    val selftext: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String?
): Parcelable