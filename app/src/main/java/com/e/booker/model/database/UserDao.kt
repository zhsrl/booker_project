package com.e.booker.model.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDao {

    @Insert
    fun registerUser(userEntity: UserEntity)
}