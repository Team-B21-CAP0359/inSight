package com.bangkit.capstone.insightapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoodModel(
    var imagePath: String? = "",
    var shirt: String? = "",
    var shirtDescription: String? = "",
    var shirtName: String? = ""
) : Parcelable
