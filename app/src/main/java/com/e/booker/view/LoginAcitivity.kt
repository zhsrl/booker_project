package com.e.booker.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.e.booker.R
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserDatabase
import com.e.booker.model.database.UserEntity
import com.e.booker.utils.SaveDataSharedPreference
import java.net.Inet4Address

class LoginAcitivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initAll()

        if(SaveDataSharedPreference.getLoggedStatus(applicationContext)){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext, "Logged: false", Toast.LENGTH_SHORT).show()
        }

        signInButton.setOnClickListener{
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()

            if(usernameText.isEmpty() || passwordText.isEmpty()){
                Toast.makeText(applicationContext, "Empty fields!", Toast.LENGTH_SHORT).show()
            }else{
                val userDatabase = DatabaseProvider.getUserDatabase(applicationContext)
                val userDao = userDatabase.userDao()

                Thread(Runnable {
                    kotlin.run {
                        val userEntity: UserEntity = userDao.login(usernameText,passwordText)
                        if(userEntity == null){
                            runOnUiThread(Runnable {
                                Toast.makeText(applicationContext, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                            })
                        }else{
                            SaveDataSharedPreference.setLogged(applicationContext,true)
                            val name = userEntity.name
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.putExtra("name", name)
                            startActivity(intent)

                        }
                    }
                }).start()
            }
        }

    }

    fun initAll(){
        username = findViewById(R.id.sign_in_usernameET)
        password = findViewById(R.id.sign_in_passwordET)
        signInButton = findViewById(R.id.sign_in_btn)
    }
}