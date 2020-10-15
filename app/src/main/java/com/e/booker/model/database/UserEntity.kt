package com.e.booker.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var userName: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "name")
    var name: String = ""



)