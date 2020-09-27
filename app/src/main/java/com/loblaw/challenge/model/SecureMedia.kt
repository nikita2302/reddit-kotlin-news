package com.loblaw.challenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SecureMedia(
    @SerializedName("oembed")
    val oembed: Oembed,
    @SerializedName("type")
    val type: String
): Parcelable