package com.bangkit.capstone.insightapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopModel(

    var alamat_perusahaan: String? = "",
    var deskripsi_barang: String? = "",
    var email: String? = "",
    var harga_barang: String? = "",
    var nama_barang: String? = "",
    var nama_perusahaan: String? = "",
    var profile_photo: String? = "",
    var search: String? = "",
    var uid: String? = "",
    var username: String? = "",
    var wa_penjual: String? = ""

) : Parcelable