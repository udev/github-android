package com.victorude.github.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GiphyPreview(
    val url: String
) : Parcelable