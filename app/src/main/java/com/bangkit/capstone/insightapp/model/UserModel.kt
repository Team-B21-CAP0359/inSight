package com.bangkit.capstone.insightapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
        var bio: String? = "",
        var email: String? = "",
        var profile_photo: String? = "",
        var search: String? = "",
        var status: String? = "",
        var uid: String? = "",
        var username: String? = "",
) : Parcelable