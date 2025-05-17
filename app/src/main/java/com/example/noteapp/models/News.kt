package com.example.noteapp.models

import java.io.Serializable

data class News(
    val title: String,
    val createdAt: String
) : Serializable
