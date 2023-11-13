package com.unideb.fvass.letswatchit.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  Movie(
    val id: Int, val title: String?, val length: String?, val extension: String?
) : Parcelable