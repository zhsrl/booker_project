package com.e.booker.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserEntity
import com.e.booker.utils.SaveDataSharedPreference
import com.e.booker.view.ui.HomeActivity
import com.e.booker.view.ui.LoginAcitivity

class LoginViewModel(var context: Context): ViewModel() {

    private var activity: LoginAcitivity = LoginAcitivity()

    fun loginUser(userEntity: UserEntity,username: String,password: String){


        val userDatabase = DatabaseProvider.getUserDatabase(context)
        val userDao = userDatabase.userDao()

        Thread(Runnable {
            kotlin.run {
                val userEntity: UserEntity = userDao.login(username,password)
                if (userEntity == null){
                    activity.runOnUiThread {
                        kotlin.run {
                            Toast.makeText(context, "Логин немесе пароль қате!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    SaveDataSharedPreference.setLogged(context,true)
                    val name = userEntity.name
                    val intent = Intent(context, HomeActivity::class.java)
                    intent.putExtra("name", name)
                    context.startActivity(intent)

                }
            }
        }).start()
    }


}