package com.e.booker.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.booker.R
import com.e.booker.model.pojo.Item
import com.e.booker.view.adapter.BookAdapter
import com.e.booker.viewmodel.activityviewmodel.SearchBookViewModel
import com.e.booker.viewmodel.ViewModelProviderFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SearchBooksActivity() : AppCompatActivity(),CoroutineScope {

    private val job = Job()

    private lateinit var bookRecView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private var list: List<Item?> = ArrayList()

    private lateinit var searchTXT: EditText
    private lateinit var searchBTN: ImageButton

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var searchBookViewModel: SearchBookViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        initAll()

        //ViewModel
        val viewModelProviderFactory = ViewModelProviderFactory(context = this)
        searchBookViewModel = ViewModelProvider(this, viewModelProviderFactory).get(SearchBookViewModel::class.java)

        //Set Adapter for RecyclerView
        setAdapter()

        searchBookViewModel.liveData.observe(this, Observer { result ->
            when(result){
                is SearchBookViewModel.State.HideLoading ->{
                    Log.e("TAG","hideLoad")
                }
                is SearchBookViewModel.State.ShowLoading ->{
                    Log.e("TAG","stateLoad")
                }
                is SearchBookViewModel.State.Result ->{
                    bookAdapter.itemList = result.list
                    bookAdapter.notifyDataSetChanged()
                }
            }
        })

        searchBookViewModel.getBooks2("history")


    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun initAll(){
        bookRecView = findViewById(R.id.book_rec_view)
        searchTXT = findViewById(R.id.search_bookET)
        searchBTN = findViewById(R.id.searchBTN)
    }



    //PROBLEM : Retrofit annotation not found
//    fun getBooks(book: String){
//        launch {
//            val response = ApiService.getBookApi().getBooks(book)
//            if(response.isSuccessful){
//                    val list = response.body()
//                    val items: List<Item?> = list!!.getItems()
//                    bookAdapter.itemList = items
//                    bookAdapter.notifyDataSetChanged()
//
//            }else{
//                Toast.makeText(applicationContext, "Che to ne to!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }



    fun setAdapter(){
        bookAdapter = BookAdapter(context = applicationContext,itemList = list)
        bookRecView.layoutManager = LinearLayoutManager(applicationContext)
        bookRecView.adapter = bookAdapter
        bookAdapter.notifyDataSetChanged()

    }



}