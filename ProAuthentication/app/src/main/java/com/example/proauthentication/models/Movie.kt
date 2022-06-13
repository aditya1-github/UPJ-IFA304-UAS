package com.example.proauthentication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    // region Attribute class disesuaikan dengan data yang didapat dari api
    @SerializedName("id")
    val id : String ?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("poster_path")
    val poster : String?,

    @SerializedName("release_date")
    val release : String?
    // endregion

) : Parcelable{
    constructor() : this("", "", "", "") // membuat contructor dengan semua data kosong
}