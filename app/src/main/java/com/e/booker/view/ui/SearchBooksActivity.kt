package com.e.booker.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.booker.R
import com.e.booker.model.api.ApiService
import com.e.booker.model.pojo.Book
import com.e.booker.model.pojo.Item
import com.e.booker.view.adapter.BookAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.coroutines.CoroutineContext

class SearchBooksActivity() : AppCompatActivity(),CoroutineScope {

    private val job = Job()

    private lateinit var bookRecView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private var list: List<Item?> = ArrayList()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        initAll()

        bookAdapter = BookAdapter(context = applicationContext,itemList = list)
        bookRecView.layoutManager = LinearLayoutManager(applicationContext)
        bookRecView.adapter = bookAdapter
        bookAdapter.notifyDataSetChanged()


        getBooks2("programming")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun initAll(){
        bookRecView = findViewById(R.id.book_rec_view)
    }

    fun getBooks(book: String){
        launch {
            val response = ApiService.getBookApi().getBooks(book)
            if(response.isSuccessful){
                    val list = response.body()
                    val items: List<Item?> = list!!.getItems()
                    bookAdapter.itemList = items
                    bookAdapter.notifyDataSetChanged()

            }else{
                Toast.makeText(applicationContext, "Che to ne to!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getBooks2(book: String){
        ApiService.getBookApi().getBooks2(book).enqueue(object : Callback<Book>{
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                val list = response.body()
                val items: List<Item?> = list!!.getItems()
                bookAdapter.itemList = items
                bookAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Toast.makeText(applicationContext, "ЧЕ ТО НЕ ТО!", Toast.LENGTH_SHORT).show()
            }

        })
    }


}