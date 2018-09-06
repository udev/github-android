package com.victorude.github.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Auth(
        val message: String
) : Parcelable