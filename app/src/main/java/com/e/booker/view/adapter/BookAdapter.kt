package com.e.booker.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.booker.R
import com.e.booker.model.pojo.Book
import com.e.booker.model.pojo.Item
import com.e.booker.model.pojo.VolumeInfo

class BookAdapter(var context: Context,var itemList: List<Item?> ): RecyclerView.Adapter<BookAdapter.ViewHolder>(){

    fun setItems(data: List<Item>){
        itemList = data
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private lateinit var bookImage: ImageView
        private lateinit var bookTitle: TextView
        private lateinit var bookAuthor: TextView

        fun bind(item: Item?){
            initAll()

            Glide.with(view)
                .load(item!!.volumeInfo.imageLinks.thumbnail)
                .into(bookImage)

            bookTitle.text = item.volumeInfo.title
            bookAuthor.text = item.volumeInfo.authors.toString()

        }

        fun initAll(){
            bookImage = view.findViewById(R.id.book_imageIV)
            bookTitle = view.findViewById(R.id.book_titleTV)
            bookAuthor = view.findViewById(R.id.book_authorTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_model, parent, false)
        val params: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }



}