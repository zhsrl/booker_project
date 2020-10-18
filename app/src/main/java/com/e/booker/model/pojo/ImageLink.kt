package com.e.booker.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageLink(
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String
)