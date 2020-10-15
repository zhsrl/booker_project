package com.e.booker.model.database

import android.content.Context
import android.service.autofill.UserData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao


}

object DatabaseProvider{
    val dbName: String = "users"

    @Volatile
    private var userDatabase: UserDatabase? = null

    fun getUserDatabase(context: Context): UserDatabase{
        synchronized(this){
            var instance = userDatabase

            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java,
                        dbName)
                        .fallbackToDestructiveMigration()
                        .build()

                userDatabase = instance
            }

            return instance
        }

    }
}