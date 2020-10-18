package com.e.booker.model.api

import com.e.booker.model.pojo.Book
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiService {

    const val BASE_URL = "https://www.googleapis.com/books/v1/"

    fun getBookApi(): BookApi{

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(BookApi::class.java)
    }

    interface BookApi{

        @GET("volumes")
        suspend fun getBooks(@Query("q")q:String): Response<Book>


        @GET("volumes")
        fun getBooks2(@Query("q")q: String): retrofit2.Call<Book>
    }

}

