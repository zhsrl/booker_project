package com.e.booker.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("authors")
    @Expose
    val authors: ArrayList<*>,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("imageLinks")
    @Expose
    var imageLinks: ImageLink
)