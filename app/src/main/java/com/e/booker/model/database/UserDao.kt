package com.e.booker.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun registerUser(userEntity: UserEntity)

    @Query("SELECT * from users where username = (:userId) and password = (:password)")
    fun login(userId: String, password: String): UserEntity
}