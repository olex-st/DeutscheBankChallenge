package com.example.typicodebook.model

data class CommentUi(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
): TextUi
