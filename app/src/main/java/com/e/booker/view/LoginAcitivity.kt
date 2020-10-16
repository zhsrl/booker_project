package com.e.booker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.e.booker.R
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserDatabase
import com.e.booker.model.database.UserEntity
import java.net.Inet4Address

class LoginAcitivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initAll()

        signUpButton.setOnClickListener{
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
        signUpButton = findViewById(R.id.sign_in_btn)
    }
}