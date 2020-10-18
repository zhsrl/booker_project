package com.e.booker.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserDao
import com.e.booker.model.database.UserDatabase
import com.e.booker.model.database.UserEntity
import com.e.booker.view.ui.RegistrationActivity
import kotlin.concurrent.thread

class RegistrationViewModel(private val context: Context): ViewModel(){
    private var activity: RegistrationActivity = RegistrationActivity()


    fun registerUser(userEntity: UserEntity){

        val userDatabase: UserDatabase

        userDatabase = DatabaseProvider.getUserDatabase(context)

        val userDao: UserDao = userDatabase.userDao()

        Thread(Runnable {
            kotlin.run {
                userDao.registerUser(userEntity)
                activity.runOnUiThread(Runnable {
                            kotlin.run {
                               Toast.makeText(context, "User registered!", Toast.LENGTH_SHORT).show()
                            }
                       })
            }
        }).start()
    }



}