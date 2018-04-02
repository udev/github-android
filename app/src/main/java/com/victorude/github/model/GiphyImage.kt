package com.victorude.github.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GiphyImage(
    val preview_gif: GiphyPreview,
    val original: GiphyOriginal
) : Parcelable