package com.e.booker.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.booker.model.api.ApiService
import com.e.booker.model.pojo.Book
import com.e.booker.model.pojo.Item
import retrofit2.Call
import retrofit2.Response

class SearchBookViewModel(private val context: Context): ViewModel() {

    val liveData = MutableLiveData<State>()

    fun getBooks2(book: String){
        liveData.value = State.ShowLoading
        ApiService.getBookApi().getBooks2(book).enqueue(object : retrofit2.Callback<Book>{
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if(response.isSuccessful){
                    val list = response.body()
                    val items: List<Item?> = list!!.getItems()
                    liveData.postValue(State.Result(items))
                    liveData.value = State.HideLoading

                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Toast.makeText(context, "ЧЕ ТО НЕ ТО!", Toast.LENGTH_SHORT).show()
                liveData.value = State.HideLoading
            }

        })
    }


    sealed class State(){
        object HideLoading: State()
        object ShowLoading: State()
        data class Result(val list: List<Item?>): State()
    }
}