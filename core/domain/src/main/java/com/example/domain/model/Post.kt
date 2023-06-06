package com.example.domain.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
) : Text {
    var favorite: Boolean = false
}
