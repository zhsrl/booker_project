package com.e.booker.model

data class User(
    var name: String ,
    var surname: String ,
    var email: String ,
    var password: String,
    var image: String = ""
)