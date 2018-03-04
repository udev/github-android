package com.victorude.github.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class License(
    val key: String,
    val name: String,
    val spdx_id: String,
    val url: String,
    val html_url: String
) : Parcelable
