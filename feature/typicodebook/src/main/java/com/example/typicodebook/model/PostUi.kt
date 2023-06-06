package com.example.typicodebook.model

data class PostUi(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val isChecked: Boolean
): TextUi
