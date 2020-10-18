package com.e.booker.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item(
    @SerializedName("volumeInfo")
    var volumeInfo: VolumeInfo
)