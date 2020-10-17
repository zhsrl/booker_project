package com.e.booker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ActionMenuView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import com.e.booker.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class HomeActivity : AppCompatActivity() {

    private lateinit var todo: ActionMenuItemView
    private lateinit var books: ActionMenuItemView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initAll()

        todo.setOnClickListener{
            Toast.makeText(applicationContext, "ToDo", Toast.LENGTH_SHORT).show()
        }

        books.setOnClickListener{
            Toast.makeText(applicationContext, "BOOKS", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, BooksActivity::class.java)
            startActivity(intent)
        }


    }

    fun initAll(){
        todo = findViewById(R.id.todoIT)
        books = findViewById(R.id.bookIT)
    }


}