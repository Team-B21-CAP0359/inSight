package com.bangkit.capstone.insightapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
        var bio: String? = "",
        var email: String? = "",
        var jenis_celana: String? = "",
        var jenis_sepatu: String? = "",
        var jenis_shirt: String? = "",
        var nama_celana: String? = "",
        var nama_sepatu: String? = "",
        var nama_shirt: String? = "",
        var profile_photo: String? = "",
        var search: String? = "",
        var status: String? = "",
        var uid: String? = "",
        var username: String? = ""
) : Parcelable