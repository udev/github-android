package com.victorude.github.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GiphyOriginal(
    val url: String
) : Parcelable