package com.loblaw.challenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Oembed(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String
): Parcelable