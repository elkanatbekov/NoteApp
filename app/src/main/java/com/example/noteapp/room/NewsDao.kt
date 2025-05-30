package com.example.noteapp.room

import android.icu.text.StringSearch
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.models.News


@Dao
interface NewsDao {

    @Query("SELECT * FROM news order by createdAt DESC" )
    fun getAll(): List<News>

    @Insert
    fun insert(news: News)

    @Delete
    fun delete(news: News): Int

    @Query("SELECT * FROM news WHERE title LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): List<News>
}