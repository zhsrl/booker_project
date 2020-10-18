package com.e.booker.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Book(
    @SerializedName("items")
    @Expose
    var items: ArrayList<Item?>
) {
    @JvmName("getItems1")
    fun getItems(): ArrayList<Item?> {
        return items
    }
}